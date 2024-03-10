package com.project.dotori.user;

import com.project.dotori.global.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class MemberBookReviewTest {

    @DisplayName("점수가 0 미만이거나 5 초과일시 예외가 발생한다.")
    @ValueSource(ints = { -1, 6 })
    @ParameterizedTest
    void validScore(Integer score) {
        // when & then
        Assertions.assertThatThrownBy(() -> MemberBookReview.builder()
            .score(score)
            .build()
        ).isInstanceOf(BusinessException.class);
    }

    @DisplayName("리뷰가 500자를 초과하면 예외가 발생한다.")
    @Test
    void validReview() {
        // given
        var randomReview = IntStream.range(0, 501)
            .mapToObj(i -> "a")
            .collect(Collectors.joining());

        // when & then
        Assertions.assertThatThrownBy(() -> MemberBookReview.builder()
            .review(randomReview)
            .build()
        ).isInstanceOf(BusinessException.class);
    }
}