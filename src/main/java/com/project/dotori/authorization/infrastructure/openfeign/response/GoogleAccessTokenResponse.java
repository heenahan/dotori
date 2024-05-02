package com.project.dotori.authorization.infrastructure.openfeign.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.dotori.authorization.application.response.Oauth2AccessTokenResponse;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleAccessTokenResponse(

    String accessToken,
    Long expiresIn,
    String refreshToken,
    String scope,
    String tokenType
) {

    public Oauth2AccessTokenResponse toService() {
        return Oauth2AccessTokenResponse.builder()
            .accessToken(accessToken)
            .build();
    }
}
