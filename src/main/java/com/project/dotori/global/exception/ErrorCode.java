package com.project.dotori.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_LENGTH("COMMON_001", "문자열 길이가 범위를 넘어섰습니다."),
    INVALID_RANGE("COMMON_002", "숫자가 범위를 넘어섰습니다."),
    INVALID_DATE("COMMON_003", "날짜가 범위를 넘어섰습니다."),
    INVALID_FORMAT("COMMON_004", "올바르지 않은 형식입니다."),
    NOT_FOUND("COMMON_005", "데이터를 찾을 수 없습니다."),
    DUPLICATED("COMMON_006", "데이터가 중복됩니다."),
    NOT_OWNER("COMMON_007", "데이터의 소유자가 아닙니다."),
    INVALID_TOKEN("COMMON_008", "옳바르지 않은 토큰입니다.");

    private final String code;
    private final String description;
}
