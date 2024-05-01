package com.project.dotori.global.config;

import com.project.dotori.book.infrastructure.openfeign.AladinProperties;
import feign.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {
    "com.project.dotori.book.infrastructure.openfeign"
})
@EnableConfigurationProperties(AladinProperties.class)
public class OpenFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
