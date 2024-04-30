package com.project.dotori.statistics.application;

import com.project.dotori.member.application.MemberValidator;
import com.project.dotori.member_book.application.MemberBookReader;
import com.project.dotori.statistics.application.response.StatisticsYearResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StatisticsService {

    private final MemberValidator memberValidator;
    private final MemberBookReader memberBookReader;

    public StatisticsYearResponse findStatisticsYear(
        Long memberId,
        Integer year
    ) {
        memberValidator.validMember(memberId);

        var statisticsYearResponse = memberBookReader.findStatisticsYearResponse(memberId, year);
        var statisticsMonthResponses = memberBookReader.findStatisticsMonthResponses(memberId, year);
        var statisticsStarAverageResponse = memberBookReader.findStatisticsStarAverageResponse(memberId, year);
        var statisticsStarRatioResponses = memberBookReader.findStatisticsStarRatioResponses(memberId, year);
        var statisticsCategoryResponses = memberBookReader.findStatisticsCategoryResponses(memberId, year);

        return StatisticsYearResponse.of(
            statisticsYearResponse,
            statisticsMonthResponses,
            statisticsStarAverageResponse,
            statisticsStarRatioResponses,
            statisticsCategoryResponses
        );
    }
}
