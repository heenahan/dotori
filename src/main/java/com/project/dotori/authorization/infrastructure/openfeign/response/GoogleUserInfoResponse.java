package com.project.dotori.authorization.infrastructure.openfeign.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.dotori.authorization.application.response.Oauth2UserInfoResponse;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleUserInfoResponse(
    String id,
    String email,
    String name,
    String picture,
    String locale
) {

    public Oauth2UserInfoResponse toService() {
        return Oauth2UserInfoResponse.builder()
            .id(id)
            .email(email)
            .name(name)
            .build();
    }
}
