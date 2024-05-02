package com.project.dotori.member.domain;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일을 생성한다.")
    @Test
    void validEmail() {
        // given
        var email = "abc@abc.com";

        // when
        var createdEmail = Email.builder()
            .email(email)
            .build();

        // then
        assertThat(createdEmail.getEmail()).isEqualTo(email);
    }

    @DisplayName("올바르지 못한 형식의 이메일은 예외가 발생한다.")
    @ValueSource(strings = { "abc", "abc@", "@", "@abc", "abc@abc", "abc@abc.", "abc@.", "abc@.com", "@abc.com" })
    @ParameterizedTest
    void invalidEmailFormat(
        String invalidEmail
    ) {
        // when & then
        assertThatThrownBy(() ->
            Email.builder()
                .email(invalidEmail)
                .build()
        ).isInstanceOf(BusinessException.class);
    }

    @DisplayName("이메일이 빈칸이거나 320자를 초과하면 예외가 발생한다.")
    @MethodSource("invalidEmail")
    @ParameterizedTest
    void invalidEmailLength(
        String invalidEmail
    ) {
        // when & then
        assertThatThrownBy(() ->
            Email.builder()
                .email(invalidEmail)
                .build()
        ).isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> invalidEmail() {
        return Stream.of(
            null,
            Arguments.arguments(""),
            Arguments.arguments(StringRandomGenerator.generate(321))
        );
    }
}