package com.project.dotori.book;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ReadingDate {

    private static final String INVALID_DATE_FORMAT = "독서 시작일과 종료일에 0이 들어갈 수 없습니다. startDate = %s, endDate = %s";
    private static final String INVALID_DATE = "독서 종료일은 시작일과 같거나 미래여야 합니다. startDate = %s, endDate = %s";

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Builder
    private ReadingDate(
        LocalDate startDate,
        LocalDate endDate
    ) {
        validTime(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validTime(
        LocalDate startDate,
        LocalDate endDate
    ) {
        if (startDate.getYear() == 0  || (Objects.nonNull(endDate) && endDate.getYear() == 0)) {
            throw new BusinessException(ErrorCode.INVALID_DATE, INVALID_DATE_FORMAT.formatted(startDate, endDate));
        }
        if (Objects.nonNull(endDate) && endDate.isBefore(startDate)) {
            throw new BusinessException(ErrorCode.INVALID_DATE, INVALID_DATE.formatted(startDate, endDate));
        }
    }
}
