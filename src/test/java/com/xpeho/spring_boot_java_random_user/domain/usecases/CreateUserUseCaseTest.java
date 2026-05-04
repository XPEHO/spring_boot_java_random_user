package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserUseCaseTest {
    private UserService userService;
    private CreateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new CreateUserUseCase(userService);
    }

    @Test
    @DisplayName("Should create a user by passing the UserEntity directly")
    void shouldCreateUserWithoutKeepingInputId() {
        UserEntity input = new UserEntity(
            null, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );
        UserEntity createdUser = new UserEntity(
            1L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US"
        );

        when(userService.save(input)).thenReturn(createdUser);

        UserEntity result = useCase.execute(input);

        assertEquals(createdUser, result);
        verify(userService).save(input);
    }
}
