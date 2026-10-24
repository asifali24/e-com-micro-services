package com.inventory_services.inventory_services.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
