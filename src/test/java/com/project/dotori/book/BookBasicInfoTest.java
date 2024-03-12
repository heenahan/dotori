package com.project.dotori.book;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.global.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class BookBasicInfoTest {

    @DisplayName("유효하지 않는 isbn, title, author, coverPath, description은 예외가 발생한다.")
    @Nested
    class LengthTest {

        @DisplayName("isbn이 빈칸이거나 13자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidIsbn")
        @ParameterizedTest
        void validIsbn(String isbn) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn(isbn)
                        .title("title")
                        .author("author")
                        .coverPath("https://")
                        .page(100)
                        .price(20000)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("title이 빈칸이거나 50자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidTitle")
        @ParameterizedTest
        void validTitle(String title) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title(title)
                        .author("author")
                        .coverPath("https://")
                        .page(100)
                        .price(20000)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("author이 빈칸이거나 50자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidAuthor")
        @ParameterizedTest
        void validAuthor(String author) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title("title")
                        .author(author)
                        .coverPath("https://")
                        .page(100)
                        .price(20000)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("coverPath이 빈칸이거나 100자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidCoverPath")
        @ParameterizedTest
        void validCoverPath(String coverPath) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title("title")
                        .author("author")
                        .coverPath(coverPath)
                        .page(100)
                        .price(20000)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("description이 빈칸이거나 1000자를 초과하면 예외가 발생한다.")
        @MethodSource("invalidDescription")
        @ParameterizedTest
        void validDescription(String description) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title("title")
                        .author("author")
                        .coverPath("https://")
                        .page(100)
                        .price(20000)
                        .description(description)
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

        static Stream<Arguments> invalidCoverPath() {
            return Stream.of(
                null,
                Arguments.arguments(""),
                Arguments.arguments(StringRandomGenerator.generate(101))
            );
        }

        static Stream<Arguments> invalidDescription() {
            return Stream.of(
                null,
                Arguments.arguments(""),
                Arguments.arguments(StringRandomGenerator.generate(1001))
            );
        }
    }

    @DisplayName("유효하지 않은 page, price는 예외가 발생한다.")
    @Nested
    class NumberTest {

        @DisplayName("page가 null이거나 음수면 예외가 발생한다.")
        @NullSource
        @ValueSource(ints = { -1 })
        @ParameterizedTest
        void validPage(Integer page) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title("title")
                        .author("author")
                        .coverPath("https://")
                        .page(page)
                        .price(20000)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }

        @DisplayName("price가 null이거나 음수면 예외가 발생한다.")
        @NullSource
        @ValueSource(ints = { -1 })
        @ParameterizedTest
        void validPrice(Integer price) {
            // when & then
            Assertions.assertThatThrownBy(() ->
                    BookBasicInfo.builder()
                        .isbn("isbn")
                        .title("title")
                        .author("author")
                        .coverPath("https://")
                        .page(100)
                        .price(price)
                        .description("description")
                        .build())
                .isInstanceOf(BusinessException.class);
        }
    }
}