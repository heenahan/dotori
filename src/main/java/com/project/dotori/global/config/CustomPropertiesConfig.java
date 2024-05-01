package com.project.dotori.global.config;

import com.project.dotori.auth.JwtProperties;
import com.project.dotori.book.infrastructure.openfeign.AladinProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    AladinProperties.class, JwtProperties.class
})
public class CustomPropertiesConfig {
}
