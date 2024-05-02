package com.project.dotori.authorization.infrastructure.openfeign.request;

import com.project.dotori.authorization.application.request.Oauth2CodeRequest;
import lombok.Builder;

@Builder
public record GoogleAccessTokenRequest(
    String clientId,
    String clientSecret,
    String code,
    String redirectUri,
    String grantType
) {

    public static GoogleAccessTokenRequest of(
        Oauth2CodeRequest request,
        String clientId,
        String clientSecret,
        String redirectUri
    ) {
        return GoogleAccessTokenRequest.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .code(request.code())
            .redirectUri(redirectUri)
            .grantType("authorization_code")
            .build();
    }
}
