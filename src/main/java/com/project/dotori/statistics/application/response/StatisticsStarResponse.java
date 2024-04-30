package com.project.dotori.statistics.application.response;

import com.project.dotori.member_book.domain.repository.response.StatisticsStarAverageQueryResponse;
import com.project.dotori.member_book.domain.repository.response.StatisticsStarQueryResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record StatisticsStarResponse(
    Double average,
    List<StarRatio> ratios
) {

    static StatisticsStarResponse from(
        StatisticsStarAverageQueryResponse averageQueryResponse,
        List<StatisticsStarQueryResponse> starQueryResponses
    ) {
        var starRatios = starQueryResponses.stream()
            .map(StarRatio::from)
            .toList();

        return StatisticsStarResponse.builder()
            .average(averageQueryResponse.starAverage())
            .ratios(starRatios)
            .build();
    }

    @Builder
    public record StarRatio(
        Float star,
        Integer percentage
    ){

        static StarRatio from(
            StatisticsStarQueryResponse response
        ) {
            return StarRatio.builder()
                .star(response.getStar())
                .percentage(response.getPercentage())
                .build();
        }
    }
}
