package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateRandomUserUseCaseTest {
    private UserService userService;
    private UpdateRandomUserUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new UpdateRandomUserUseCase(userService);
    }

    @Test
    @DisplayName("Should update an existing user and preserve its id")
    void shouldUpdateExistingUserAndPreserveItsId() {
        UserEntity existingUser = new UserEntity(
            42L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR"
        );
        UserRequest request = new UserRequest(
            "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );
        UserEntity savedUser = new UserEntity(
            42L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );

        when(userService.getById(42)).thenReturn(Optional.of(existingUser));
        when(userService.save(new UserEntity(
            42L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        ))).thenReturn(savedUser);

        UserEntity result = useCase.execute(42, request);

        assertEquals(savedUser, result);
        verify(userService).getById(42);
        verify(userService).save(new UserEntity(
            42L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        ));
    }

    @Test
    @DisplayName("Should throw when updating a user that does not exist")
    void shouldThrowWhenUserDoesNotExist() {
        UserRequest request = new UserRequest(
            "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );

        when(userService.getById(99)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> useCase.execute(99, request)
        );

        assertEquals("User not found with id: 99", exception.getMessage());
        verify(userService).getById(99);
        verify(userService, never()).save(any(UserEntity.class));
    }
}
