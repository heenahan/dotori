package com.project.dotori.global.config;

import com.project.dotori.book.infrastructure.openfeign.AladinConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    AladinConfig.class
})
public class CustomPropertiesConfig {
}
