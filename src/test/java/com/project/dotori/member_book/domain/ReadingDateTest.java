package com.project.dotori.member_book.domain;

import com.project.dotori.member_book.domain.ReadingDate;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReadingDateTest {

    @DisplayName("독서 종료일이 null 시작일보다 현재거나 미래이다.")
    @ParameterizedTest
    @MethodSource("invalidPublishDate")
    void validReadingDate(LocalDate startDate, LocalDate endDate) {
        // when
        var readingDate = ReadingDate.builder()
            .startDate(startDate)
            .endDate(endDate)
            .build();

        // then
        assertThat(readingDate.getStartDate()).isEqualTo(startDate);
        assertThat(readingDate.getEndDate()).isEqualTo(endDate);
    }

    @DisplayName("독서 시작일과 종료일에 0이 들어가면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidPublishDateFormat")
    void invalidReadingDateFormat(LocalDate startDate, LocalDate endDate) {
        // when & then
        assertThatThrownBy(() -> {
            ReadingDate.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        }).isInstanceOf(BusinessException.class);
    }

    @DisplayName("독서 종료일이 시작일보다 과거라면 예외가 발생한다.")
    @Test
    void invalidReadingDate() {
        // given
        var startDate = LocalDate.now();
        var endDate = startDate.minusDays(1);

        // when & then
        assertThatThrownBy(() -> {
            ReadingDate.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        }).isInstanceOf(BusinessException.class);
    }

    static Stream<Arguments> invalidPublishDate() {
        return Stream.of(
            Arguments.arguments(LocalDate.now(), null),
            Arguments.arguments(LocalDate.now(), LocalDate.now()),
            Arguments.arguments(LocalDate.now(), LocalDate.now().plusDays(1))
        );
    }

    static Stream<Arguments> invalidPublishDateFormat() {
        return Stream.of(
            Arguments.arguments(LocalDate.of(0, 1, 1), null),
            Arguments.arguments(LocalDate.of(2024, 1, 1), LocalDate.of(0, 1, 1))
        );
    }
}