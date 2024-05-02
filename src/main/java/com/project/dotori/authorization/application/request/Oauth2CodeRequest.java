package com.project.dotori.authorization.application.request;

import lombok.Builder;

@Builder
public record Oauth2CodeRequest(
    String code
) {

    public static Oauth2CodeRequest from(
        GoogleCodeServiceRequest request
    ) {
        return Oauth2CodeRequest.builder()
            .code(request.code())
            .build();
    }
}
