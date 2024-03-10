package com.project.dotori.user;

import com.project.dotori.global.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @DisplayName("닉네임이 빈칸이거나 길이를 초과하면 에러가 발생한다.")
    @NullSource
    @ValueSource(strings = { "",  "12345678910" })
    @ParameterizedTest
    void validNickname(String nickname) {
        // when & then
        Assertions.assertThatThrownBy(() ->
            Nickname.builder()
                .nickname(nickname)
                .build()
        ).isInstanceOf(BusinessException.class);
    }
}