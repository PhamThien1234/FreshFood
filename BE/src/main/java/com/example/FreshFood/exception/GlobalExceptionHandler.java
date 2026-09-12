package com.example.FreshFood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                errorCode.name(),
                errorCode.getMessage(),
                LocalDateTime.now()

        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    public record ErrorResponse(
            String code,
            String message,
            LocalDateTime timestamp
    ) {
    }
}
