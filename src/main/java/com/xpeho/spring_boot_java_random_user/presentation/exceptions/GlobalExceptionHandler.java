package com.xpeho.spring_boot_java_random_user.presentation.exceptions;

import com.xpeho.spring_boot_java_random_user.domain.exceptions.InvalidPaginationException;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPaginationException(InvalidPaginationException ex) {
        logger.warn("Invalid pagination request: {}", ex.getMessage());
        return buildErrorResponse("INVALID_PAGINATION", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());
        logger.warn("Type mismatch: {}", message);
        return buildErrorResponse("INVALID_PARAMETER", message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        logger.warn("User not found: {}", ex.getMessage());
        return buildErrorResponse("USER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred", ex);
        return buildErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String error, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(error, message, status.value()));
    }
}
