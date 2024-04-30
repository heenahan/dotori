package com.project.dotori.member.presentation;

import com.project.dotori.RestDocsSupport;
import com.project.dotori.statistics.application.StatisticsService;
import com.project.dotori.statistics.application.response.StatisticsCategoryResponse;
import com.project.dotori.statistics.application.response.StatisticsMonthlyResponse;
import com.project.dotori.statistics.application.response.StatisticsStarResponse;
import com.project.dotori.statistics.application.response.StatisticsYearResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;


import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends RestDocsSupport {

    @MockBean
    private StatisticsService statisticsService;

    @Override
    protected Object setController() {
        return new MemberController(statisticsService);
    }

    @DisplayName("멤버의 연간 통계를 조회한다.")
    @Test
    void findStatisticsYear() throws Exception {
        // given
        var year = 2024;
        var response = createStatisticsYearResponse();
        given(statisticsService.findStatisticsYear(anyLong(), anyInt())).willReturn(response);

        mockMvc.perform(get("/api/v1/me/statistics/year")
                .contentType(MediaType.APPLICATION_JSON)
                .param("year", String.valueOf(year))
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(document("find-statistics-year",
                queryParameters(
                    parameterWithName("year").description("넌도")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverTime").type(JsonFieldType.STRING).description("현재 서버 시간"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.totalReadCount").type(JsonFieldType.NUMBER).description("연간 읽은 책의 총 수"),
                    fieldWithPath("data.bookHeight").type(JsonFieldType.NUMBER).description("책 쌓인 높이"),
                    fieldWithPath("data.monthly").type(JsonFieldType.OBJECT).description("월별 통계"),
                    fieldWithPath("data.monthly.mostReadMonth").type(JsonFieldType.OBJECT).description("가장 많이 읽은 달"),
                    fieldWithPath("data.monthly.mostReadMonth.month").type(JsonFieldType.NUMBER).description("달"),
                    fieldWithPath("data.monthly.mostReadMonth.count").type(JsonFieldType.NUMBER).description("읽은 책의 수"),
                    fieldWithPath("data.monthly.averageReadMonth").type(JsonFieldType.NUMBER).description("월별 평균 읽은 책의 수"),
                    fieldWithPath("data.monthly.monthlyCounts").type(JsonFieldType.ARRAY).description("월별 책 읽은 수"),
                    fieldWithPath("data.monthly.monthlyCounts[].month").type(JsonFieldType.NUMBER).description("달"),
                    fieldWithPath("data.monthly.monthlyCounts[].count").type(JsonFieldType.NUMBER).description("월별 읽은 책의 수"),
                    fieldWithPath("data.star").type(JsonFieldType.OBJECT).description("별점 통계"),
                    fieldWithPath("data.star.average").type(JsonFieldType.NUMBER).description("별점 평균"),
                    fieldWithPath("data.star.ratios").type(JsonFieldType.ARRAY).description("별점 비율"),
                    fieldWithPath("data.star.ratios[].star").type(JsonFieldType.NUMBER).description("별점"),
                    fieldWithPath("data.star.ratios[].percentage").type(JsonFieldType.NUMBER).description("해당 별점의 비율"),
                    fieldWithPath("data.categories").type(JsonFieldType.OBJECT).description("카테고리 통계 (Top 5)"),
                    fieldWithPath("data.categories.ratios").type(JsonFieldType.ARRAY).description("카테고리 비율"),
                    fieldWithPath("data.categories.ratios[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                    fieldWithPath("data.categories.ratios[].categoryName").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("data.categories.ratios[].count").type(JsonFieldType.NUMBER).description("해당 카테고리의 책의 수"),
                    fieldWithPath("data.categories.ratios[].percentage").type(JsonFieldType.NUMBER).description("해당 카테고리의 비율")
                )
                )
            );
    }

    private StatisticsYearResponse createStatisticsYearResponse() {
        var max = new StatisticsMonthlyResponse.MonthlyCount(4, 2);
        var random = RandomGenerator.getDefault();
        var monthlyCounts = IntStream.range(1, 13)
            .mapToObj(i -> new StatisticsMonthlyResponse.MonthlyCount(i, random.nextInt(0, 11)))
            .toList();
        var statisticsMonthlyResponse = StatisticsMonthlyResponse.builder()
            .mostReadMonth(max)
            .averageReadMonth(3.5d)
            .monthlyCounts(monthlyCounts)
            .build();

        var ratios = new ArrayList<StatisticsStarResponse.StarRatio>();
        for (float i = 0.0f; i <= 5.0f; i+= 0.5f) {
            ratios.add(new StatisticsStarResponse.StarRatio(i, 9));
        }
        var statisticsStarResponse = StatisticsStarResponse.builder()
            .average(3.5d)
            .ratios(ratios)
            .build();

        var category1 = StatisticsCategoryResponse.CategoryRatio.builder()
            .categoryId(1L)
            .categoryName("컴퓨터공학")
            .count(5L)
            .percentage(50L)
            .build();
        var category2 = StatisticsCategoryResponse.CategoryRatio.builder()
            .categoryId(2L)
            .categoryName("한국소설")
            .count(3L)
            .percentage(30L)
            .build();
        var category3 = StatisticsCategoryResponse.CategoryRatio.builder()
            .categoryId(3L)
            .categoryName("가정/요리")
            .count(2L)
            .percentage(20L)
            .build();
        var statisticsCategoryResponse = new StatisticsCategoryResponse(List.of(category1, category2, category3));

        return StatisticsYearResponse.builder()
            .totalReadCount(5L)
            .bookHeight(8L)
            .monthly(statisticsMonthlyResponse)
            .star(statisticsStarResponse)
            .categories(statisticsCategoryResponse)
            .build();
    }
}