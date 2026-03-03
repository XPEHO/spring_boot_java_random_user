package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserByIdUserUseCaseTest {
    private UserService userService;
    private GetUserByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new GetUserByIdUseCase(userService);
    }

    @Test
    @DisplayName("Should return user when user exists")
    void shouldReturnUserWhenFound() {
        UserEntity expected = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "http://pic.jpg", "FR");
        when(userService.getById(1L)).thenReturn(Optional.of(expected));

        UserEntity result = useCase.execute(1L);

        assertEquals(expected, result);
        verify(userService).getById(1L);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user does not exist")
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userService.getById(99L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> useCase.execute(99L)
        );

        assertTrue(exception.getMessage().contains("99"));
        verify(userService).getById(99L);
    }

    @Test
    @DisplayName("Should call userService exactly once with the given id")
    void shouldCallUserServiceOnce() {
        UserEntity user = new UserEntity(5L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "http://pic2.jpg", "US");
        when(userService.getById(5L)).thenReturn(Optional.of(user));

        useCase.execute(5L);

        verify(userService, times(1)).getById(5L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Should not call userService with a different id")
    void shouldNotCallUserServiceWithDifferentId() {
        UserEntity user = new UserEntity(3L, "male", "Bob", "Brown", "Mr", "bob@example.com", "0622222222", "http://pic3.jpg", "DE");
        when(userService.getById(3L)).thenReturn(Optional.of(user));

        useCase.execute(3L);

        verify(userService, never()).getById(42L);
    }
}
