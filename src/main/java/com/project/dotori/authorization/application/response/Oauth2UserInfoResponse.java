package com.project.dotori.authorization.application.response;

import com.project.dotori.member.application.request.MemberCreateRequest;
import com.project.dotori.member.domain.Role;
import lombok.Builder;

@Builder
public record Oauth2UserInfoResponse(
    String id,
    String email,
    String name
) {

    public MemberCreateRequest toService() {
        return MemberCreateRequest.from(
            id,
            email,
            name,
            Role.GOOGLE
        );
    }
}
