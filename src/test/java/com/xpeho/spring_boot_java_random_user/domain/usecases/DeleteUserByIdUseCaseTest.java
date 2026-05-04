package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteUserByIdUseCaseTest {
    private UserService userService;
    private DeleteUserByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new DeleteUserByIdUseCase(userService);
    }

    @Test
    @DisplayName("Should call userService deleteById when executing")
    void shouldCallDeleteByIdWhenExecuting() {
        long userId = 1L;

        useCase.execute(userId);

        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Should call userService deleteById with correct id")
    void shouldCallDeleteByIdWithCorrectId() {
        long userId = 42L;

        useCase.execute(userId);

        verify(userService).deleteById(42L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Should call userService deleteById for different ids")
    void shouldCallDeleteByIdForDifferentIds() {
        useCase.execute(1L);
        useCase.execute(2L);
        useCase.execute(3L);

        verify(userService).deleteById(1L);
        verify(userService).deleteById(2L);
        verify(userService).deleteById(3L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @DisplayName("Should not throw exception when deleting existing user")
    void shouldNotThrowExceptionWhenDeletingExistingUser() {
        long userId = 5L;
        doNothing().when(userService).deleteById(userId);

        useCase.execute(userId);

        verify(userService).deleteById(userId);
    }
}
