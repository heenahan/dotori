package com.project.dotori.member.presentation;

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
@RequestMapping("/api/v1/me")
@RestController
public class MemberController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics/year")
    public ResponseEntity<ApiResponse<StatisticsYearResponse>> findStatisticsYear(
        @Param("year") Integer year
    ) {
        var memberId = 1L;
        var response = statisticsService.findStatisticsYear(memberId, year);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
