package com.project.dotori.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverTime;

    private final T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            LocalDateTime.now(),
            data
        );
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
            HttpStatus.CREATED.value(),
            LocalDateTime.now(),
            data
        );
    }

    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(
            HttpStatus.NO_CONTENT.value(),
            LocalDateTime.now(),
            null
        );
    }
}