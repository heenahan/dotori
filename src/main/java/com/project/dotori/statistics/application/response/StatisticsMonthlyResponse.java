package com.project.dotori.statistics.application.response;

import com.project.dotori.member_book.domain.repository.response.StatisticsMonthQueryResponse;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Builder
public record StatisticsMonthlyResponse(
    MonthlyCount mostReadMonth, // 가장 많이 읽은 달
    Double averageReadMonth, // 평균 독서량
    List<MonthlyCount> monthlyCounts
) {

    public static StatisticsMonthlyResponse from(
        List<StatisticsMonthQueryResponse> responses
    ) {
        var max = responses.stream()
            .max(comparing(StatisticsMonthQueryResponse::getCnt)
                .thenComparing(StatisticsMonthQueryResponse::getMonths)
            ).orElseGet(() -> null);
        var maxMonthly = Optional.ofNullable(max)
            .map(MonthlyCount::from)
            .orElseGet(() -> new MonthlyCount(0, 0));

        var average = responses.stream()
            .mapToInt(StatisticsMonthQueryResponse::getCnt)
            .average()
            .orElseGet(() -> 0.0d);
        var monthlyCounts = responses.stream()
            .map(MonthlyCount::from)
            .toList();

        return StatisticsMonthlyResponse.builder()
            .mostReadMonth(maxMonthly)
            .averageReadMonth(Math.round(average * 10)/10.0d)
            .monthlyCounts(monthlyCounts)
            .build();
    }

    @Builder
    public record MonthlyCount(
        Integer month,
        Integer count
    ) {

        static MonthlyCount from(
            StatisticsMonthQueryResponse response
        ) {
            return MonthlyCount.builder()
                .month(response.getMonths())
                .count(response.getCnt())
                .build();
        }
    }
}
