package com.project.dotori.member_book.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.repository.response.MemberBookDetailQueryResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Optional;

@Builder
public record MemberBookDetailResponse(
    String isbn,

    String coverPath,

    String title,

    String author,

    String publisher,

    int totalPage,

    String categoryName,

    String memberBookStatus,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate startDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate endDate,

    float star,

    int page,

    int percent,

    String bookLevel
) {

    public static MemberBookDetailResponse from(
        MemberBookDetailQueryResponse response
    ) {
        var bookLevel = Optional.ofNullable(response.bookLevel())
            .map(BookLevel::name)
            .orElseGet(() -> null);

        return MemberBookDetailResponse.builder()
            .isbn(response.isbn())
            .coverPath(response.coverPath())
            .title(response.title())
            .author(response.author())
            .publisher(response.publisher())
            .totalPage(response.totalPage())
            .categoryName(response.categoryName())
            .memberBookStatus(response.memberBookStatus().name())
            .startDate(response.startDate())
            .endDate(response.endDate())
            .star(response.star())
            .page(response.page())
            .percent(response.percent())
            .bookLevel(bookLevel)
            .build();
    }
}
