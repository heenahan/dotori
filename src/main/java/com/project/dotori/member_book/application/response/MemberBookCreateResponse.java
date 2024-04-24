package com.project.dotori.member_book.application.response;

import com.project.dotori.member_book.domain.MemberBook;

public record MemberBookCreateResponse(
    Long memberBookId
) {

    public static MemberBookCreateResponse from(
        MemberBook memberBook
    ) {
        return new MemberBookCreateResponse(memberBook.getId());
    }
}
