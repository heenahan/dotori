package com.project.dotori.member.domain;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class MemberTest {

    @DisplayName("socialId가 빈칸이거나 30자를 초과하면 예외가 발생한다.")
    @MethodSource("invalidSocialIdLength")
    @ParameterizedTest
    void invalidSocialId(
        String socialId
    ) {
        // when & then
        Assertions.assertThatThrownBy(() ->
            Member.builder()
                .socialId(socialId)
                .email("abc@abc.com")
                .nickname("nickname")
                .role(Role.GOOGLE)
                .build()
        ).isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> invalidSocialIdLength() {
        return Stream.of(
            null,
            Arguments.arguments(""),
            Arguments.arguments(
                StringRandomGenerator.generate(31)
            )
        );
    }
}