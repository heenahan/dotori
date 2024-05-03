package com.project.dotori.member.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member {

    private static final String INVALID_SOCIAL_ID = "socialId는 빈칸이거나 30자를 초과해선 안됩니다. length = %d";

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
        validSocialId(socialId);
        this.socialId = socialId;
        this.email = Email.builder()
            .email(email)
            .build();
        this.nickname = Nickname.builder()
            .nickname(nickname)
            .build();
        this.role = role;
    }

    private void validSocialId(
        String socialId
    ) {
        if (StringUtils.isBlank(socialId) || socialId.length() > 30) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_SOCIAL_ID.formatted(StringUtils.length(socialId)));
        }
    }
}
