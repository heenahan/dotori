package com.project.dotori.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", length = 30, nullable = false)
    private String socialId;

    @Embedded
    private Email email;

    @Embedded
    private Nickname nickname;

    @Column(name = "roles", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private Member(
        String socialId,
        String email,
        String nickname,
        Role role
    ) {
        this.socialId = socialId;
        this.email = Email.builder()
            .email(email)
            .build();
        this.nickname = Nickname.builder()
            .nickname(nickname)
            .build();
        this.role = role;
    }
}
