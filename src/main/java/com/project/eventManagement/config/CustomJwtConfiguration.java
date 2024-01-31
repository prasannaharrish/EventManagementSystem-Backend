package com.project.eventManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.project.eventManagement.advice.TokenRevokedAuthenticationEntryPoint;

@Configuration
public class CustomJwtConfiguration {

    @Bean
    @Lazy
    public CustomJwtAuthenticationConverter jwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter();
    }

    @Bean
    public TokenRevokedAuthenticationEntryPoint tokenRevokedAuthenticationEntryPoint() {
        return new TokenRevokedAuthenticationEntryPoint();
    }
}
