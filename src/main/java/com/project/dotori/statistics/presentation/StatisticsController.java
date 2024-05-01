package com.project.dotori.statistics.presentation;

import com.project.dotori.auth.MemberId;
import com.project.dotori.global.response.ApiResponse;
import com.project.dotori.statistics.application.StatisticsService;
import com.project.dotori.statistics.application.response.StatisticsYearResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/year")
    public ResponseEntity<ApiResponse<StatisticsYearResponse>> findStatisticsYear(
        @MemberId Long memberId,
        @Param("year") Integer year
    ) {
        var response = statisticsService.findStatisticsYear(memberId, year);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
