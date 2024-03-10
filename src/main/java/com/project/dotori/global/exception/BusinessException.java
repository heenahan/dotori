package com.project.dotori.global.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public BusinessException(
        ErrorCode errorCode,
        String message
    ) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
