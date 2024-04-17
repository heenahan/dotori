package com.project.dotori.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookLevel {

    EASY("쉬움"),
    A_LITTLE_EASY("조금 쉬움"),
    MEDIUM("보통"),
    A_LITTLE_DIFFICULT("조금 어려움"),
    DIFFICULT("어려움");

    private final String description;
}
