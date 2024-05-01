package com.project.dotori.global.exception;

import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public AuthorizationException(
        ErrorCode errorCode,
        String message
    ) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
