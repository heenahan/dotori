package com.project.dotori.book.domain;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookBasicInfoTest {

    @DisplayName("유효하지 않는 isbn, title, author은 예외가 발생한다.")
    @Nested
    class LengthTest {

        @DisplayName("title이 빈칸이거나 50자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidTitle")
        @ParameterizedTest
        void validTitle(String title) {
            // when & then
            assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .title(title)
                        .author("author")
                        .page(100)
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("author이 빈칸이거나 50자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidAuthor")
        @ParameterizedTest
        void validAuthor(String author) {
            // when & then
            assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .title("title")
                        .author(author)
                        .page(100)
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        static Stream<Arguments> invalidTitle() {
            return Stream.of(
                null,
                Arguments.arguments(""),
                Arguments.arguments(StringRandomGenerator.generate(51))
            );
        }

        static Stream<Arguments> invalidAuthor() {
            return Stream.of(
                null,
                Arguments.arguments(""),
                Arguments.arguments(StringRandomGenerator.generate(51))
            );
        }
    }

    @DisplayName("유효하지 않은 page는 예외가 발생한다.")
    @Nested
    class NumberTest {

        @DisplayName("page가 null이거나 음수면 예외가 발생한다.")
        @NullSource
        @ValueSource(ints = { -1 })
        @ParameterizedTest
        void validPage(Integer page) {
            // when & then
            assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .title("title")
                        .author("author")
                        .page(page)
                        .build())
                .isInstanceOf(BusinessException.class);
        }
    }
}