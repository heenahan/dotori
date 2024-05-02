package com.project.dotori;

import com.project.dotori.authorization.application.jwt.JwtProperties;
import com.project.dotori.authorization.application.jwt.JwtGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JwtTestConfiguration {

    @Bean
    public JwtProperties jwtProperties() {
        var randomKey = StringRandomGenerator.generate(56);
        return new JwtProperties(1000L, randomKey);
    }

    @Bean
    public JwtGenerator JwtTokenManager(JwtProperties jwtProperties) {
        return new JwtGenerator(jwtProperties);
    }
}
