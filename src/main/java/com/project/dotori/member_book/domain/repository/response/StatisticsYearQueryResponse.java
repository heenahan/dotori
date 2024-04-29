package com.project.dotori.member_book.domain.repository.response;

public record StatisticsYearQueryResponse(
    Long totalReadCount, // 완독 권수
    Long totalPage // 완독한 독서 총 페이지 수
) {
}
