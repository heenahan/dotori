package com.project.dotori.authorization.application.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Jwt {
    AUTH_TYPE("Bearer"),
    KEY("memberId");

    private final String description;
}
