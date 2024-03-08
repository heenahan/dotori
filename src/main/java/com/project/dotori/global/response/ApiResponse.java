package com.project.dotori.global.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int statusCode;
    private final T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            data
        );
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
            HttpStatus.CREATED.value(),
            data
        );
    }

    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(
            HttpStatus.NO_CONTENT.value(),
            null
        );
    }
}