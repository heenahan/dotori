package com.project.dotori.member_book.application.request;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Optional;

@Builder
public record MemberBookUpdateServiceRequest(
    Long memberBookId,

    LocalDate startDate,

    LocalDate endDate,

    int page,

    float star,

    BookLevel bookLevel,

    MemberBookStatus memberBookStatus
) {


    public static MemberBookUpdateServiceRequest from(
        Long memberBookId,
        LocalDate startDate,
        LocalDate endDate,
        Integer page,
        Float star,
        String bookLevel,
        String memberBookStatus
    ) {
        var convertedBookLevel = Optional.ofNullable(bookLevel)
            .map(BookLevel::from)
            .orElseGet(() -> null);

        return MemberBookUpdateServiceRequest.builder()
            .memberBookId(memberBookId)
            .startDate(startDate)
            .endDate(endDate)
            .page(page)
            .star(star)
            .bookLevel(convertedBookLevel)
            .memberBookStatus(MemberBookStatus.from(memberBookStatus))
            .build();
    }

    public MemberBook toEntity(
        Integer totalPage
    ) {
        return MemberBook.builder()
            .memberBookStatus(memberBookStatus)
            .startDate(startDate)
            .endDate(endDate)
            .totalPage(totalPage)
            .page(page)
            .star(star)
            .bookLevel(bookLevel)
            .build();
    }
}
