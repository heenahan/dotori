package com.project.dotori.member_book.domain;

import com.project.dotori.global.exception.BusinessException;
import com.project.dotori.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum BookLevel {

    EASY("쉬움"),
    A_LITTLE_EASY("조금 쉬움"),
    NORMAL("보통"),
    A_LITTLE_HARD("조금 어려움"),
    HARD("어려움");

    private static final String NOT_FOUND = "BookLevel에서 적절한 값을 찾을 수 없습니다. value = %s";
    private final String description;

    public static BookLevel from(
        String bookLevel
    ) {
        return Arrays.stream(BookLevel.values())
            .filter(bl -> Objects.equals(bl.name(), bookLevel))
            .findFirst()
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, NOT_FOUND.formatted(bookLevel)));
    }
}
