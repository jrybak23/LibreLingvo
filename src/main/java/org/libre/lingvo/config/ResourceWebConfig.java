package org.libre.lingvo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * Created by igorek2312 on 11.10.16.
 */
@Configuration
public class ResourceWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/ValidationMessages");
        return messageSource;
    }

    @Bean
    public ReloadableResourceBundleMessageSource backendMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/Messages");
        return messageSource;
    }

    @Bean
    public SerializableResourceBundleMessageSource angularMessageSource() {
        SerializableResourceBundleMessageSource messageSource = new SerializableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/AngularMessages");
        return messageSource;
    }
}
