package com.project.dotori.global.config;

import com.project.dotori.auth.AuthorizationIntercepter;
import com.project.dotori.auth.JwtGenerator;
import com.project.dotori.auth.MemberIdArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtGenerator jwtGenerator;
    private final MemberIdArgumentResolver memberIdArgumentResolver;

    @Bean
    public AuthorizationIntercepter authorizationIntercepter() {
        return new AuthorizationIntercepter(jwtGenerator);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationIntercepter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdArgumentResolver);
    }
}
