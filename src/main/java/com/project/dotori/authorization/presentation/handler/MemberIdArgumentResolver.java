package com.project.dotori.authorization.presentation.handler;

import com.project.dotori.authorization.application.jwt.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var memberId = parameter.hasParameterAnnotation(MemberId.class);
        var longType = Long.class.isAssignableFrom(parameter.getParameterType());

        return memberId && longType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute(Jwt.KEY.getDescription());
    }
}
