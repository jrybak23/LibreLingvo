package org.libre.lingvo.config.security;

import org.libre.lingvo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import javax.sql.DataSource;

/**
 * Created by igorek2312 on 08.09.16.
 */

@Configuration
public class OAuth2ServerConfiguration {
    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Bean
        public RoleHierarchyImpl roleHierarchy() {
            RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
            roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
            return roleHierarchy;
        }

        private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
            DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
            defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
            return defaultWebSecurityExpressionHandler;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .expressionHandler(webExpressionHandler())
                    .anyRequest().permitAll();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    @ComponentScan
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private DataSource dataSource;

        @Bean
        TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("clientapp")
                    .authorizedGrantTypes("password")
                    .scopes("use")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456")
                    //.accessTokenValiditySeconds(30);
                    .accessTokenValiditySeconds(3600*24*7);
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }

    }
}