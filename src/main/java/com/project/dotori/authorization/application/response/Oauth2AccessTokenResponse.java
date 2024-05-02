package com.project.dotori.authorization.application.response;

import lombok.Builder;

@Builder
public record Oauth2AccessTokenResponse(
    String accessToken,
    String tokenType
) {
}
