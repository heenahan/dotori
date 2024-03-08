package com.project.dotori.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_NULL("COMMON_001", "필수 값 입니다. filed name : %s"),

    INVALID_NICKNAME("MEMBER_001", "닉네임은 빈칸이어서는 안돼고 10자 이하여야 합니다."),

    INVALID_SCORE("MEMBER_BOOK_001", "점수는 0이상 5미만이어야 합니다."),
    INVALID_REVIEW("MEMBER_BOOK_002", "리뷰는 빈칸이거나 500자 이하여야 합니다."),

    INTERNAL_SERVER_ERROR("SERVER_001", "서버에서 알 수 없는 에러가 발생했습니다.");

    private final String code;
    private final String description;
}
