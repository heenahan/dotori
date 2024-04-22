package com.project.dotori.member_book.application.request;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBook;
import com.project.dotori.member_book.domain.MemberBookRecord;
import com.project.dotori.member_book.domain.MemberBookStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberBookServiceRequest(
    String isbn,
    LocalDate startDate,
    LocalDate endDate,
    Integer page,
    Float star,
    BookLevel bookLevel,
    MemberBookStatus memberBookStatus
) {

    public static MemberBookServiceRequest from(
        String isbn,
        LocalDate startDate,
        LocalDate endDate,
        Integer page,
        Float star,
        String bookLevel,
        String memberBookStatus
    ) {
        return MemberBookServiceRequest.builder()
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
        Long memberId
    ) {
        return MemberBook.builder()
            .memberId(memberId)
            .bookId(isbn)
            .memberBookStatus(memberBookStatus)
            .build();
    }

    public MemberBookRecord toEntity(
        Long memberBookId,
        Integer totalPage
    ) {
        return MemberBookRecord.builder()
            .memberBookId(memberBookId)
            .startDate(startDate)
            .endDate(endDate)
            .page(page)
            .totalPage(totalPage)
            .star(star)
            .bookLevel(bookLevel)
            .build();
    }
}
