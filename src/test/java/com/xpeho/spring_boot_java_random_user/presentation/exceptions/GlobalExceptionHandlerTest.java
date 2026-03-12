package com.xpeho.spring_boot_java_random_user.presentation.exceptions;

import com.xpeho.spring_boot_java_random_user.domain.exceptions.InvalidPaginationException;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should return 400 BAD_REQUEST when InvalidPaginationException is thrown")
    void shouldReturnBadRequestWhenInvalidPaginationException() {
        InvalidPaginationException ex = new InvalidPaginationException("Page size cannot exceed 30");
        ResponseEntity<ErrorResponse> response = handler.handleInvalidPaginationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_PAGINATION", response.getBody().error());
        assertEquals("Page size cannot exceed 30", response.getBody().message());
        assertEquals(400, response.getBody().status());
    }

    @Test
    @DisplayName("Should return 400 BAD_REQUEST when MethodArgumentTypeMismatchException is thrown")
    void shouldReturnBadRequestWhenMethodArgumentTypeMismatch() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "INVALID", UserSource.class, "source", null, null
        );
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_PARAMETER", response.getBody().error());
        assertEquals(400, response.getBody().status());
    }

    @Test
    @DisplayName("Should return 404 NOT_FOUND when UserNotFoundException is thrown")
    void shouldReturnNotFoundWhenUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException(42);
        ResponseEntity<ErrorResponse> response = handler.handleUserNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("USER_NOT_FOUND", response.getBody().error());
        assertEquals(404, response.getBody().status());
    }

    @Test
    @DisplayName("Should return 500 INTERNAL_SERVER_ERROR for generic exceptions")
    void shouldReturnInternalServerErrorForGenericException() {
        Exception ex = new Exception("Something went wrong");
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().error());
        assertEquals(500, response.getBody().status());
    }
}

