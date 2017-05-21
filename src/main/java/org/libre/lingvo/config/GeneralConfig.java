package org.libre.lingvo.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan
public class GeneralConfig {

    @Bean(name = "main")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
