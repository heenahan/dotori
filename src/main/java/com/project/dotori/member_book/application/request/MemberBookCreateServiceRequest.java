package com.project.dotori.member_book.application.request;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberBookCreateServiceRequest(
    String isbn,
    LocalDate startDate,
    LocalDate endDate,
    int page,
    Float star,
    BookLevel bookLevel,
    MemberBookStatus memberBookStatus
) {

    public static MemberBookCreateServiceRequest from(
        String isbn,
        LocalDate startDate,
        LocalDate endDate,
        int page,
        Float star,
        String bookLevel,
        String memberBookStatus
    ) {
        return MemberBookCreateServiceRequest.builder()
            .isbn(isbn)
            .startDate(startDate)
            .endDate(endDate)
            .page(page)
            .star(star)
            .bookLevel(BookLevel.from(bookLevel))
            .memberBookStatus(MemberBookStatus.from(memberBookStatus))
            .build();
    }

    public MemberBook toEntity(
        Long memberId,
        int totalPage
    ) {
        return MemberBook.builder()
            .memberId(memberId)
            .bookId(isbn)
            .memberBookStatus(memberBookStatus)
            .startDate(startDate)
            .endDate(endDate)
            .page(page)
            .totalPage(totalPage)
            .star(star)
            .bookLevel(bookLevel)
            .build();
    }
}
