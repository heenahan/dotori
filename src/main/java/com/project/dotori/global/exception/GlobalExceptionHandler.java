package com.project.dotori.global.exception;


import com.project.dotori.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
        BusinessException e
    ) {
        final var errorResponse = ErrorResponse.badRequest(e.getErrorCode(), e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(
        EntityNotFoundException e
    ) {
        final var errorResponse = ErrorResponse.notFound(e.getErrorCode(), e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(
//        Exception e
//    ) {
//        log.error("서버에서 알 수 없는 에러 발생하여 핸들링 exception : ", e);
//        final var errorResponse = ErrorResponse.internalServerError(ErrorCode.INTERNAL_SERVER_ERROR);
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
