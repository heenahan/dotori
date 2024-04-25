package com.project.dotori.member_book.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBookStatus;
import com.project.dotori.member_book.domain.repository.response.MemberBookQueryResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberBookResponse(
    String coverPath,

    String title,

    String author,

    int totalPage,

    Long memberBookId,

    MemberBookStatus memberBookStatus,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate startDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate endDate,

    float star,

    int page,

    int percent,

    BookLevel bookLevel
) {

    public static MemberBookResponse from(
        MemberBookQueryResponse response
    ) {
        return MemberBookResponse.builder()
            .coverPath(response.coverPath())
            .title(response.title())
            .author(response.author())
            .totalPage(response.totalPage())
            .memberBookStatus(response.memberBookStatus())
            .startDate(response.startDate())
            .endDate(response.endDate())
            .star(response.star())
            .page(response.page())
            .percent(response.percent())
            .bookLevel(response.bookLevel())
            .build();
    }
}
