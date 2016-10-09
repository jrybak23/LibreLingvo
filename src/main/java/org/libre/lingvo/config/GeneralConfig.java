package org.libre.lingvo.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Configuration
@ComponentScan
public class GeneralConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("credentials.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }


    @Bean(name = "main")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public URL originUrl() {
        try {
            return new URL("http://localhost:9000");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public URL originEnableUserUrl() {
        try {
            return new URL(originUrl().toString() + "/enable-user/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public URL originCancelUserEnablingUrl() {
        try {
            return new URL(originUrl().toString() + "/cancel-user-enabling/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Timer timer() {
        return new Timer();
    }

}
