package com.project.dotori.member_book.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.member_book.application.request.MemberBookCreateServiceRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberBookCreateRequest(

    @NotNull(message = "isbn은 필수값입니다.")
    String isbn,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate startDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate endDate,

    int page,

    Float star,

    String bookLevel,

    @NotNull(message = "독서 상태(memberBookStatus)는 필수값입니다.")
    String memberBookStatus
) {

    public MemberBookCreateServiceRequest toService() {
        return MemberBookCreateServiceRequest.from(
            isbn,
            startDate,
            endDate,
            page,
            star,
            bookLevel,
            memberBookStatus
        );
    }
}
