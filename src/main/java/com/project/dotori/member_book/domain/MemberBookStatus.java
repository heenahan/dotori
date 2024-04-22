package com.project.dotori.member_book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum MemberBookStatus {

    TO_READ("읽을 예정"),
    READING("읽는 중"),
    READ("읽음");

    private static final String NOT_FOUND = "MemeberBookStatus에서 적절한 값을 찾을 수 없습니다. value = %s";
    private final String description;

    public static MemberBookStatus from(
        String memberBookStatus
    ) {
        return Arrays.stream(MemberBookStatus.values())
            .filter(mb -> Objects.equals(mb.name(), memberBookStatus))
            .findFirst()
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(memberBookStatus)));
    }
}