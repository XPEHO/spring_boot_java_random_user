package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserUseCaseTest {
    private LocalUserService userService;
    private CreateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(LocalUserService.class);
        useCase = new CreateUserUseCase(userService);
    }

    @Test
    @DisplayName("Should create a user without keeping the input id")
    void shouldCreateUserWithoutKeepingInputId() {
        UserRequest payload = new UserRequest(
            "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );
        UserEntity createdUser = new UserEntity(
            1L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );
        UserEntity expectedSavedUser = new UserEntity(
            null, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );

        when(userService.save(expectedSavedUser)).thenReturn(createdUser);

        UserEntity result = useCase.execute(payload);

        assertEquals(createdUser, result);
        verify(userService).save(expectedSavedUser);
    }
}
