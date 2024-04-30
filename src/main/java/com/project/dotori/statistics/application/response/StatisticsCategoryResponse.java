package com.project.dotori.statistics.application.response;

import com.project.dotori.member_book.domain.repository.response.StatisticsCategoryQueryResponse;
import lombok.Builder;

import java.util.List;

public record StatisticsCategoryResponse(
    List<CategoryRatio> ratios
) {

    static StatisticsCategoryResponse from(
        List<StatisticsCategoryQueryResponse> responses
    ) {
        var ratios = responses.stream()
            .map(CategoryRatio::from)
            .toList();

        return new StatisticsCategoryResponse(ratios);
    }

    @Builder
    public record CategoryRatio(
        Long categoryId,
        String categoryName,
        Long count,
        Long percentage
    ) {

        static CategoryRatio from(
            StatisticsCategoryQueryResponse response
        ) {
            return CategoryRatio.builder()
                .categoryId(response.getCategoryId())
                .categoryName(response.getCategoryName())
                .count(response.getCnt())
                .percentage(response.getPercentage())
                .build();
        }
    }
}
