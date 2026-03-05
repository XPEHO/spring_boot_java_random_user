package com.xpeho.spring_boot_java_random_user.presentation;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.FetchAndSaveRandomUsersUseCase;
import com.xpeho.spring_boot_java_random_user.domain.usecases.GetUserByIdUseCase;
import com.xpeho.spring_boot_java_random_user.domain.usecases.UpdateRandomUserUseCase;
import com.xpeho.spring_boot_java_random_user.presentation.handlers.UserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserHandlerTest {

    private FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;
    private UpdateRandomUserUseCase updateRandomUserUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        fetchAndSaveRandomUsersUseCase = mock(FetchAndSaveRandomUsersUseCase.class);
        updateRandomUserUseCase = mock(UpdateRandomUserUseCase.class);
        getUserByIdUseCase = mock(GetUserByIdUseCase.class);
        userHandler = new UserHandler(fetchAndSaveRandomUsersUseCase, updateRandomUserUseCase, getUserByIdUseCase);
    }

    @Test
    @DisplayName("Should return 200 and users when getRandomUsers succeeds")
    void shouldReturnOkWhenGetRandomUsersSucceeds() throws IOException {
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "pic.jpg", "FR")
        );
        when(fetchAndSaveRandomUsersUseCase.execute(2)).thenReturn(users);

        ResponseEntity<List<UserEntity>> response = userHandler.getRandomUsers(2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(fetchAndSaveRandomUsersUseCase, times(1)).execute(2);
    }

    @Test
    @DisplayName("Should return 500 and empty list when getRandomUsers throws IOException")
    void shouldReturnInternalServerErrorWhenGetRandomUsersFails() throws IOException {
        when(fetchAndSaveRandomUsersUseCase.execute(5)).thenThrow(new IOException("downstream unavailable"));

        ResponseEntity<List<UserEntity>> response = userHandler.getRandomUsers(5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() != null && response.getBody().isEmpty());
        verify(fetchAndSaveRandomUsersUseCase, times(1)).execute(5);
    }

    @Test
    @DisplayName("Should return 200 and user when getUserById succeeds")
    void shouldReturnOkWhenGetUserByIdSucceeds() {
        UserEntity user = new UserEntity(42L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "alice.jpg", "US");
        when(getUserByIdUseCase.execute(42)).thenReturn(user);

        ResponseEntity<UserEntity> response = userHandler.getUserById(42);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(getUserByIdUseCase, times(1)).execute(42);
    }

    @Test
    @DisplayName("Should return 404 when getUserById throws UserNotFoundException")
    void shouldReturnNotFoundWhenGetUserByIdFails() {
        when(getUserByIdUseCase.execute(99)).thenThrow(new UserNotFoundException(99));

        ResponseEntity<UserEntity> response = userHandler.getUserById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(getUserByIdUseCase, times(1)).execute(99);
    }

    @Test
    @DisplayName("Should return 200 and updated user when updateRandomUser succeeds")
    void shouldReturnOkWhenUpdateRandomUserSucceeds() {
        UserRequest request = new UserRequest("female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        UserEntity updated = new UserEntity(7L, "female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        when(updateRandomUserUseCase.execute(7, request)).thenReturn(updated);

        ResponseEntity<UserEntity> response = userHandler.updateRandomUser(7, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(updateRandomUserUseCase, times(1)).execute(7, request);
    }

    @Test
    @DisplayName("Should return 404 when updateRandomUser throws UserNotFoundException")
    void shouldReturnNotFoundWhenUpdateRandomUserFails() {
        UserRequest request = new UserRequest("male", "Bob", "Brown", "Mr", "bob@example.com", "0633333333", "bob.jpg", "DE");
        when(updateRandomUserUseCase.execute(123, request)).thenThrow(new UserNotFoundException(123));

        ResponseEntity<UserEntity> response = userHandler.updateRandomUser(123, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(updateRandomUserUseCase, times(1)).execute(123, request);
    }
}
