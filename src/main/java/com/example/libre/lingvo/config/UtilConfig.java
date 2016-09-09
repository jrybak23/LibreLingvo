package com.example.libre.lingvo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Configuration
public class UtilConfig {

    @Bean(name = "main")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
