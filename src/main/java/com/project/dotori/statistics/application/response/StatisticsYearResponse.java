package com.project.dotori.statistics.application.response;

import com.project.dotori.member_book.domain.repository.response.*;
import lombok.Builder;

import java.util.List;

@Builder
public record StatisticsYearResponse(
    Long totalReadCount, // 완독 권수
    Long bookHeight, // 총 높이 100 -> 1cm
    StatisticsMonthlyResponse monthly, // 월별 통계
    StatisticsStarResponse star, // 별점
    StatisticsCategoryResponse categories // 카테고리 랭킹 Top5
) {

    public static StatisticsYearResponse of(
        StatisticsYearQueryResponse statisticsYearQueryResponse,
        List<StatisticsMonthQueryResponse> statisticsMonthQueryResponses,
        StatisticsStarAverageQueryResponse statisticsStarAverageQueryResponse,
        List<StatisticsStarQueryResponse> statisticsStarQueryResponses,
        List<StatisticsCategoryQueryResponse> statisticsCategoryResponses
    ) {
        return StatisticsYearResponse.builder()
            .totalReadCount(statisticsYearQueryResponse.totalReadCount())
            .bookHeight(statisticsYearQueryResponse.totalPage() / 200)
            .monthly(StatisticsMonthlyResponse.from(statisticsMonthQueryResponses))
            .star(StatisticsStarResponse.from(statisticsStarAverageQueryResponse, statisticsStarQueryResponses))
            .categories(StatisticsCategoryResponse.from(statisticsCategoryResponses))
            .build();
    }
}
