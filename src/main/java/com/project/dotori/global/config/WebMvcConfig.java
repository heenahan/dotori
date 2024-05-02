package com.project.dotori.global.config;

import com.project.dotori.authorization.presentation.interceptor.AuthorizationInterceptor;
import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.authorization.presentation.handler.MemberIdArgumentResolver;
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
    public AuthorizationInterceptor authorizationIntercepter() {
        return new AuthorizationInterceptor(jwtGenerator);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationIntercepter())
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns("/api/v1/auth/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdArgumentResolver);
    }
}
