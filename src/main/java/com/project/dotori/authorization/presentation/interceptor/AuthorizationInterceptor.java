package com.project.dotori.authorization.presentation.interceptor;

import com.project.dotori.authorization.application.jwt.Jwt;
import com.project.dotori.authorization.application.jwt.JwtGenerator;
import com.project.dotori.global.exception.AuthorizationException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String NOT_HEADER = "Authorization 헤더가 없습니다.";

    private final JwtGenerator jwtGenerator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // preflight 요청 처리
        if (Objects.equals(request.getMethod(), HttpMethod.OPTIONS.name())) {
            return true;
        }

        // handler가 HandlerMethod가 아닌 경우
        if (!(handler instanceof HandlerMethod)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return false;
        }

        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorization) || !authorization.startsWith(Jwt.AUTH_TYPE.getDescription())) {
            throw new AuthorizationException(ErrorCode.INVALID_TOKEN, NOT_HEADER);
        }

        var split = authorization.split(" ");
        var claims = jwtGenerator.getClaims(split[1].trim());
        request.setAttribute(Jwt.KEY.getDescription(), claims);
        return true;
    }
}