package com.project.dotori.member_book.domain.repository.response;

import com.project.dotori.member_book.domain.BookLevel;
import com.project.dotori.member_book.domain.MemberBookStatus;

import java.time.LocalDate;

public record MemberBookDetailQueryResponse(
    String isbn,
    String coverPath,
    String title,
    String author,
    String publisher,
    int totalPage,
    String categoryName,
    MemberBookStatus memberBookStatus,
    LocalDate startDate,
    LocalDate endDate,
    float star,
    int page,
    int percent,
    BookLevel bookLevel
) {
}
