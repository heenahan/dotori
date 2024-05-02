package com.project.dotori.authorization.application.response;

import com.project.dotori.authorization.application.jwt.Jwt;
import lombok.Builder;

@Builder
public record LoginResponse(
    String accessToken,
    String tokenType
) {

    public static LoginResponse from(
        String accessToken
    ) {
        return LoginResponse.builder()
            .accessToken(accessToken)
            .tokenType(Jwt.AUTH_TYPE.getDescription())
            .build();
    }
}
