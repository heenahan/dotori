package com.project.dotori.member_book.domain.repository.response;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBookStatus;

import java.time.LocalDate;

public record MemberBookQueryResponse(
    String coverPath,
    String title,
    String author,
    int totalPage,
    Long memberBookId,
    MemberBookStatus memberBookStatus,
    LocalDate startDate,
    LocalDate endDate,
    float star,
    int page,
    int percent,
    BookLevel bookLevel
) {
}
