package com.xpeho.spring_boot_java_random_user.presentation.exceptions;

import com.xpeho.spring_boot_java_random_user.domain.exceptions.InvalidPaginationException;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should return 400 BAD_REQUEST when ConstraintViolationException is thrown")
    void shouldReturnBadRequestWhenConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(jakarta.validation.Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("size");
        when(violation.getMessage()).thenReturn("must be less than or equal to 30");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));
        ResponseEntity<ErrorResponse> response = handler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INVALID_PARAMETER", response.getBody().error());
        assertEquals(400, response.getBody().status());
        assertTrue(response.getBody().message().contains("must be less than or equal to 30"));
    }

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

