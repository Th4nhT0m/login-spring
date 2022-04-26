package com.example.login.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = {"com.example.login.entities"})
@EnableJpaRepositories(basePackages = {"com.example.login.repository"})
@ComponentScan(basePackages = {"com.example.login"})
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
