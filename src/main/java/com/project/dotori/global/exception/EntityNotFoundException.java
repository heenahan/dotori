package com.project.dotori.global.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public EntityNotFoundException(
        ErrorCode errorCode,
        String message
    ) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
