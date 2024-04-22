package com.project.dotori.member_book.presentation.response;

import com.project.dotori.member_book.domain.MemberBook;

public record MemberBookResponse(
    Long memberBookId
) {

    public static MemberBookResponse from(
        MemberBook memberBook
    ) {
        return new MemberBookResponse(memberBook.getId());
    }
}
