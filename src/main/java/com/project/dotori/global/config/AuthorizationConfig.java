package com.project.dotori.global.config;

import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.authorization.application.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AuthorizationConfig {

    @Bean
    public JwtGenerator jwtGenerator(
        JwtProperties jwtProperties
    ) {
        return new JwtGenerator(jwtProperties);
    }
}
