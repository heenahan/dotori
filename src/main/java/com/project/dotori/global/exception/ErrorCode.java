package com.project.dotori.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_LENGTH("COMMON_001", "문자열 길이가 범위를 넘어섰습니다."),
    INVALID_RANGE("COMMON_002", "숫자가 범위를 넘어섰습니다.");

    private final String code;
    private final String description;
}
