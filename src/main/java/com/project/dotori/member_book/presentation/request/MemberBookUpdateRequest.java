package com.project.dotori.member_book.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.dotori.member_book.application.request.MemberBookUpdateServiceRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberBookUpdateRequest(

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

    public MemberBookUpdateServiceRequest toService(
        Long memberBookId
    ) {
        return MemberBookUpdateServiceRequest.from(
            memberBookId,
            startDate,
            endDate,
            page,
            star,
            bookLevel,
            memberBookStatus
        );
    }
}
