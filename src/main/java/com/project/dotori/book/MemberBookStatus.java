package com.project.dotori.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberBookStatus {

    TO_READ("읽을 예정"),
    READING("읽는 중"),
    READ("읽음");

    private final String description;
}