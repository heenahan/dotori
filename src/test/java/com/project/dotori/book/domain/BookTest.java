package com.project.dotori.book.domain;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {

    @DisplayName("isbn이 빈칸이거나 13자를 초과하면 예외가 발생한다.")
    @MethodSource("invalidIsbn")
    @ParameterizedTest
    void validIsbn(String isbn) {
        // when & then
        assertThatThrownBy(() ->
            Book.builder()
                .isbn(isbn)
                .build())
            .isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> invalidIsbn() {
        return Stream.of(
            null,
            Arguments.arguments(""),
            Arguments.arguments(StringRandomGenerator.generate(14))
        );
    }
}