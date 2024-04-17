package com.project.dotori.book;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookReview {

    private static final String INVALID_STAR_RANGE = "star는 0.0이상 5.0이하의 유리수여야 합니다. value = %f";
    private static final String INVALID_STAR_DECIMAL = "star의 소수점은 0이거나 5이어야 합니다. value = %f";

    @Column(name = "page", nullable = false)
    private Integer page = 0;

    @Column(name = "star", nullable = true)
    private Float star;

    @Enumerated(EnumType.STRING)
    @Column(name = "level",length = 10, nullable = true)
    private BookLevel bookLevel;

    @Builder
    private BookReview(
        Integer page,
        Float star,
        BookLevel bookLevel
    ) {
        validStar(star);
        this.page = page;
        this.star = star;
        this.bookLevel = bookLevel;
    }

    private void validStar(
        Float star
    ) {
        if (star < 0.0 || star > 5.0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_STAR_RANGE.formatted(star));
        }
        if (star % 1 != 0.0 && star % 1 != 0.5) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_STAR_DECIMAL.formatted(star));
        }
    }
}
