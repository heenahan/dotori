package com.project.dotori.authorization.application.request;

import com.project.dotori.authorization.application.response.Oauth2AccessTokenResponse;
import lombok.Builder;

@Builder
public record Oauth2AccessTokenRequest(
    String accessToken,
    String tokenType
) {

    public static Oauth2AccessTokenRequest from(
        Oauth2AccessTokenResponse response
    ) {
        return Oauth2AccessTokenRequest.builder()
            .accessToken(response.accessToken())
            .tokenType(response.tokenType())
            .build();
    }
}
