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

class BookLevelTest {

    @DisplayName("String을 BookLevel로 변환한다.")
    @MethodSource("bookLevel")
    @ParameterizedTest
    void from(
        String name,
        BookLevel bookLevel
    )  {
        // when
        var result = BookLevel.from(name);

        // then
        assertThat(result).isEqualTo(bookLevel);
    }

    @DisplayName("잘못된 값이 들어오면 예외가 발생한다.")
    @Test
    void from()  {
        // when
        var weiredName = "weiredName";

        // then
        assertThatThrownBy(() -> BookLevel.from(weiredName))
            .isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> bookLevel() {
        return Stream.of(
            Arguments.arguments("EASY", BookLevel.EASY),
            Arguments.arguments("A_LITTLE_EASY", BookLevel.A_LITTLE_EASY),
            Arguments.arguments("MEDIUM", BookLevel.MEDIUM),
            Arguments.arguments("A_LITTLE_DIFFICULT", BookLevel.A_LITTLE_DIFFICULT),
            Arguments.arguments("DIFFICULT", BookLevel.DIFFICULT)
        );
    }
}