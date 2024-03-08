package com.project.dotori.global.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public BusinessException(
        ErrorCode errorCode
    ) {
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }

    public BusinessException(
        ErrorCode errorCode,
        List<String> filedNames
    ) {
        var names = String.join(", ", filedNames);

        this.errorCode = errorCode;
        this.message = errorCode.getDescription().formatted(names);
    }
}
