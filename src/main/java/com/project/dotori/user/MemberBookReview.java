package com.project.dotori.user;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberBookReview {

    private static final String INVALID_SCORE = "점수는 0-5점 사이어야 합니다. value = %d";
    private static final String INVALID_REVIEW = "리뷰는 500자 이하여야 합니다. length = %d";

    @Column(name = "score", nullable = true)
    private Integer score;

    @Column(name = "review", length = 500, nullable = true)
    private String review;

    @Builder
    private MemberBookReview(
        Integer score,
        String review
    ) {
        validScore(score);
        validReview(review);

        this.score = score;
        this.review = review;
    }

    private void validScore(
        Integer score
    ) {
        if (score != null && (score < 0 || score > 5)) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_SCORE.formatted(score));
        }
    }

    private void validReview(
        String review
    ) {
        if (review != null && review.length() > 500) {
            throw new BusinessException(ErrorCode.INVALID_LENGTH, INVALID_REVIEW.formatted(review.length()));
        }
    }
}
