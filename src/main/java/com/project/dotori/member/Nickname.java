package com.project.dotori.member;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {

    private static final String INVALID_NICKNAME = "닉네임은 빈칸이어서는 안돼고 10자 이하여야 합니다. length = %d";

    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Builder
    private Nickname(
        String nickname
    ) {
        validNickname(nickname);
        this.nickname = nickname;
    }

    private void validNickname(
        String nickname
    ) {
        if (StringUtils.isBlank(nickname) || nickname.length() > 10) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_NICKNAME.formatted(StringUtils.length(nickname)));
        }
    }
}
