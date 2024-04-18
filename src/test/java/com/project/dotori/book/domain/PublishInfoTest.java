package com.project.dotori.book.domain;

import com.project.dotori.StringRandomGenerator;
import com.project.dotori.book.domain.PublishInfo;
import com.project.dotori.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class PublishInfoTest {

    @DisplayName("출판사는 빈칸이거나 50자를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidPublisher")
    void validPublisher(String publisher) {
        // when & then
        assertThatThrownBy(() ->
                PublishInfo.builder()
                    .publisher(publisher)
                    .publishDate(LocalDate.now().minusYears(1))
                    .build())
            .isInstanceOf(BusinessException.class);
    }

    @DisplayName("출판 일자는 null이거나 오늘보다 미래라면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidPublishDate")
    void invalidPublishDate(LocalDate publishDate) {
        // when & then
        assertThatThrownBy(() ->
            PublishInfo.builder()
                .publisher("출판사")
                .publishDate(publishDate)
                .build())
            .isInstanceOf(BusinessException.class)
            .hasMessage("출판 일자는 null이거나 오늘보다 미래여서는 안됩니다. value = %s".formatted(publishDate));
    }

    @DisplayName("출판 일자의 날짜에 0이 들어가면 예외가 발생한다.")
    @Test
    void invalidPublishDateZero() {
        // given
        var publishDate = LocalDate.of(0, 12, 30);

        // when & then
        assertThatThrownBy(() ->
            PublishInfo.builder()
                .publisher("출판사")
                .publishDate(publishDate)
                .build())
            .isInstanceOf(BusinessException.class)
            .hasMessage("출판 일자에 0이 들어가서는 안됩니다. value = %s".formatted(publishDate));
    }

    static Stream<Arguments> invalidPublisher() {
        return Stream.of(
            null,
            Arguments.arguments(""),
            Arguments.arguments(StringRandomGenerator.generate(51))
        );
    }

    static Stream<Arguments> invalidPublishDate() {
        return Stream.of(
            null,
            Arguments.arguments(LocalDate.now().plusDays(1))
        );
    }
}