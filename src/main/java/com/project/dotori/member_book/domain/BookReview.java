package com.project.dotori.member_book.domain;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BookReview {

    private static final String INVALID_PAGE = "page는 정수여아합니다. value = %d";
    private static final String INVALID_TOTAL_PAGE = "totalPage는 정수여야합니다. value = %d";
    private static final String INVALID_STAR_RANGE = "star는 0.0이상 5.0이하의 유리수여야 합니다. value = %f";
    private static final String INVALID_STAR_DECIMAL = "star의 소수점은 0이거나 5이어야 합니다. value = %f";

    @Column(name = "page", nullable = false)
    private int page = 0;

    @Column(name = "percentage", nullable = false)
    private int percentage = 0;

    @Column(name = "star", nullable = true)
    private Float star;

    @Enumerated(EnumType.STRING)
    @Column(name = "level",length = 10, nullable = true)
    private BookLevel bookLevel;

    @Builder
    private BookReview(
        int page,
        int totalPage,
        Float star,
        BookLevel bookLevel
    ) {
        validNumber(page, totalPage);
        validStar(star);
        this.page = limitPage(page, totalPage);
        this.percentage = calculatePercentage(this.page, totalPage);
        this.star = star;
        this.bookLevel = bookLevel;
    }

    private void validStar(
        Float star
    ) {
        if (Objects.nonNull(star) && (star < 0.0 || star > 5.0)) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_STAR_RANGE.formatted(star));
        }
        if (Objects.nonNull(star) && (star % 1 != 0.0 && star % 1 != 0.5)) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_STAR_DECIMAL.formatted(star));
        }
    }

    private void validNumber(
        Integer page,
        Integer totalPage
    ) {
        if (page < 0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_PAGE.formatted(page));
        }
        if (totalPage < 0) {
            throw new BusinessException(ErrorCode.INVALID_RANGE, INVALID_TOTAL_PAGE.formatted(totalPage));
        }
    }

    private Integer limitPage(
        int page,
        int totalPage
    ) {
        return Math.min(page, totalPage);
    }

    private int calculatePercentage(
        int page,
        int totalPage
    ) {
        if (totalPage == 0) {
            return 0;
        }
        var totalPageBigDecimal = BigDecimal.valueOf(totalPage);
        var pageBigDecimal = BigDecimal.valueOf(page);
        var oneHundred = BigDecimal.valueOf(100);
        var percent = pageBigDecimal.divide(totalPageBigDecimal, 2, RoundingMode.DOWN).multiply(oneHundred);

        return percent.intValue();
    }
}
