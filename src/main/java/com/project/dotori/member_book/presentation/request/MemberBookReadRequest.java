package com.project.dotori.member_book.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.member_book.application.request.MemberBookServiceRequest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberBookReadRequest(
    @NotNull(message = "책의 isbn은 필수값입니다.")
    String isbn,

    @NotNull(message = "독서 시작일은 필수값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate startDate,

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate endDate,

    int page,

    Float star,

    String bookLevel,

    @NotNull(message = "독서 상태는 필수값입니다.")
    String memberBookStatus
) {

    public MemberBookServiceRequest toService() {
        return MemberBookServiceRequest.from(isbn, startDate, endDate, page, star, bookLevel, memberBookStatus);
    }
}
