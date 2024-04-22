package com.project.dotori.member_book.domain;

import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberBookStatusTest {

    @DisplayName("String을 MemberBookStatus으로 변환한다.")
    @MethodSource("memberBookStatus")
    @ParameterizedTest
    void from(
        String name,
        MemberBookStatus memberBookStatus
    )  {
        // when
        var result = MemberBookStatus.from(name);

        // then
        assertThat(result).isEqualTo(memberBookStatus);
    }

    @DisplayName("잘못된 값이 들어오면 예외가 발생한다.")
    @Test
    void from()  {
        // when
        var weiredName = "weiredName";

        // then
        assertThatThrownBy(() -> MemberBookStatus.from(weiredName))
            .isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> memberBookStatus() {
        return Stream.of(
            Arguments.arguments("TO_READ", MemberBookStatus.TO_READ),
            Arguments.arguments("READ", MemberBookStatus.READ),
            Arguments.arguments("READING", MemberBookStatus.READING)
        );
    }
}