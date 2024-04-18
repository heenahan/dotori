package com.project.dotori.member_book.domain;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.BookReview;
import com.project.dotori.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookReviewTest {

    @DisplayName("star는 0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0만 가질 수 있다.")
    @ValueSource(floats = { 0.0f, 0.5f, 1.0f, 1.5f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 4.5f, 5.0f })
    @ParameterizedTest
    void validStar(Float star) {
        // when
        var bookReview = BookReview.builder()
            .page(100)
            .star(star)
            .bookLevel(BookLevel.MEDIUM)
            .build();

        // then
        assertThat(bookReview.getStar()).isEqualTo(star);
    }

    @DisplayName("star는 음수이거나 5를 초과하는 수라면 예외가 발생한다.")
    @ValueSource(floats = { -0.1f, 5.1f })
    @ParameterizedTest
    void invalidStarOverRange(Float star) {
        // when & then
        assertThatThrownBy(() -> {
            BookReview.builder()
                .page(100)
                .star(star)
                .bookLevel(BookLevel.MEDIUM)
                .build(); })
            .isInstanceOf(BusinessException.class)
            .hasMessage("star는 0.0이상 5.0이하의 유리수여야 합니다. value = %f".formatted(star));
    }

    @DisplayName("star는 소수점이 0.0 혹은 0.5가 아닐 때 예외가 발생한다.")
    @ValueSource(floats = { 0.1f, 1.2f, 2.2f, 3.3f, 4.4f, 0.6f, 1.7f, 2.8f, 3.9f})
    @ParameterizedTest
    void invalidStarDecimal(Float star) {
        // when & then
        assertThatThrownBy(() -> {
            BookReview.builder()
                .page(100)
                .star(star)
                .bookLevel(BookLevel.MEDIUM)
                .build(); })
            .isInstanceOf(BusinessException.class)
            .hasMessage("star의 소수점은 0이거나 5이어야 합니다. value = %f".formatted(star));
    }
}