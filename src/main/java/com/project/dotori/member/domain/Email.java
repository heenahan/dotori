package com.project.dotori.member.domain;

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
public class Email {

    private static final String EMAIL_REGEX = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
    private static final String INVALID_LENGTH = "이메일은 빈칸이어선 안되고 320자 이하여야 합니다. length = %d";
    private static final String INVALID_FORMAT = "올바른 이메일 형식이 아닙니다. value = %s";

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Builder
    private Email(
        String email
    ) {
        validEmail(email);
        this.email = email;
    }

    private void validEmail(
        String email
    ) {
        if (StringUtils.isBlank(email) || email.length() > 320) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_LENGTH.formatted(StringUtils.length(email)));
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT, INVALID_FORMAT.formatted(email));
        }
    }
}
