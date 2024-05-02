package com.project.dotori.member.application.request;

import com.project.dotori.member.domain.Member;
import com.project.dotori.member.domain.Role;
import lombok.Builder;

@Builder
public record MemberCreateRequest(
    String socialId,
    String email,
    String name,
    Role role
) {

    public static MemberCreateRequest from(
        String socialId,
        String email,
        String name,
        Role role
    ) {
        return MemberCreateRequest.builder()
            .socialId(socialId)
            .email(email)
            .name(name)
            .role(role)
            .build();
    }

    public Member toEntity() {
        return Member.builder()
            .socialId(socialId)
            .email(email)
            .nickname(name)
            .role(role)
            .build();
    }
}
