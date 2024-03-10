package com.project.dotori.global.response;

import com.project.dotori.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String errorCode;
    private final String message;

    private ErrorResponse(
        int statusCode,
        String errorCode,
        String message
    ) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse badRequest(
        ErrorCode errorCode,
        String message
    ) {
        final var status = HttpStatus.BAD_REQUEST.value();
        return new ErrorResponse(status, errorCode.getCode(), message);
    }

    public static ErrorResponse notFound(
        ErrorCode errorCode,
        String message
    ) {
        final var status = HttpStatus.NOT_FOUND.value();
        return new ErrorResponse(status, errorCode.getCode(), message);
    }

    public static ErrorResponse internalServerError(
        ErrorCode errorCode,
        String message
    ) {
        final var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return new ErrorResponse(status, errorCode.getCode(), message);
    }
}
