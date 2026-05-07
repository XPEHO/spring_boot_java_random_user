package com.xpeho.spring_boot_java_random_user.presentation;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserRequest;
import com.xpeho.spring_boot_java_random_user.presentation.handlers.UserHandler;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOHandlerTest {

    private FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;
    private UpdateRandomUserUseCase updateRandomUserUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private CreateUserUseCase createUserUseCase;
    private DeleteUserByIdUseCase deleteUserUseCase;
    private FilterUsersUseCase filterUsersUseCase;
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        fetchAndSaveRandomUsersUseCase = mock(FetchAndSaveRandomUsersUseCase.class);
        updateRandomUserUseCase = mock(UpdateRandomUserUseCase.class);
        getUserByIdUseCase = mock(GetUserByIdUseCase.class);
        createUserUseCase = mock(CreateUserUseCase.class);
        deleteUserUseCase = mock(DeleteUserByIdUseCase.class);
        filterUsersUseCase = mock(FilterUsersUseCase.class);
        userHandler = new UserHandler(fetchAndSaveRandomUsersUseCase, updateRandomUserUseCase, getUserByIdUseCase, createUserUseCase, deleteUserUseCase, filterUsersUseCase);
    }

    @Test
    @DisplayName("Should return 200 and paged users when getRandomUsers succeeds")
    void shouldReturnOkWhenGetRandomUsersSucceeds() throws IOException {
        int page = 1;
        int size = 10;
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "pic.jpg", "FR")
        );
        PaginatedUsers paginatedUsers = new PaginatedUsers(users, 50, 0, 10);
        when(fetchAndSaveRandomUsersUseCase.execute(page, size, UserSource.DUMMY)).thenReturn(paginatedUsers);

        ResponseEntity<UserResponseDTO> response = userHandler.getRandomUsers(page, size, UserSource.DUMMY);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(users, response.getBody().data());
        assertEquals(50, response.getBody().total());
        assertEquals(0, response.getBody().skip());
        assertEquals(10, response.getBody().limit());
        verify(fetchAndSaveRandomUsersUseCase, times(1)).execute(page, size, UserSource.DUMMY);
    }

    @Test
    @DisplayName("Should return 500 when getRandomUsers throws IOException")
    void shouldReturnInternalServerErrorWhenGetRandomUsersFails() throws IOException {
        int page = 1;
        int size = 10;
        when(fetchAndSaveRandomUsersUseCase.execute(page, size, UserSource.RANDOM_USER)).thenThrow(new IOException("downstream unavailable"));

        ResponseEntity<UserResponseDTO> response = userHandler.getRandomUsers(page, size, UserSource.RANDOM_USER);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(fetchAndSaveRandomUsersUseCase, times(1)).execute(page, size, UserSource.RANDOM_USER);
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
        UserEntity expectedEntity = new UserEntity(null, "female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        when(updateRandomUserUseCase.execute(7, expectedEntity)).thenReturn(updated);

        ResponseEntity<UserEntity> response = userHandler.updateRandomUser(7, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(updateRandomUserUseCase, times(1)).execute(7, expectedEntity);
    }

    @Test
    @DisplayName("Should return 404 when updateRandomUser throws UserNotFoundException")
    void shouldReturnNotFoundWhenUpdateRandomUserFails() {
        UserRequest request = new UserRequest("male", "Bob", "Brown", "Mr", "bob@example.com", "0633333333", "bob.jpg", "DE");
        UserEntity expectedEntity = new UserEntity(null, "male", "Bob", "Brown", "Mr", "bob@example.com", "0633333333", "bob.jpg", "DE");
        when(updateRandomUserUseCase.execute(123, expectedEntity)).thenThrow(new UserNotFoundException(123));

        ResponseEntity<UserEntity> response = userHandler.updateRandomUser(123, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(updateRandomUserUseCase, times(1)).execute(123, expectedEntity);
    }

    @Test
    @DisplayName("Should return 201 and created user when createUser succeeds")
    void shouldReturnCreatedWhenCreateUserSucceeds() {
        UserRequest request = new UserRequest("female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        UserEntity expectedEntity = new UserEntity(null, "female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        UserEntity created = new UserEntity(10L, "female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        when(createUserUseCase.execute(expectedEntity)).thenReturn(created);

        ResponseEntity<UserEntity> response = userHandler.createUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(createUserUseCase, times(1)).execute(expectedEntity);
    }

    @Test
    @DisplayName("Should return 204 when deleteUserById succeeds")
    void shouldReturnNoContentWhenDeleteUserByIdSucceeds() {
        int userId = 42;

        userHandler.deleteUserById(userId);

        verify(deleteUserUseCase, times(1)).execute(userId);
    }

    @Test
    @DisplayName("Should log warning when deleteUserById throws UserNotFoundException")
    void shouldLogWarningWhenDeleteUserByIdFails() {
        int userId = 123;
        doThrow(new UserNotFoundException(userId)).when(deleteUserUseCase).execute(userId);

        userHandler.deleteUserById(userId);

        verify(deleteUserUseCase, times(1)).execute(userId);
    }

    @Test
    @DisplayName("Should return 200 and filtered page when filterUsers succeeds")
    void shouldReturnOkWhenFilterUsersSucceeds() {
        UserFilter filter = new UserFilter(Gender.MALE, null, null, null, null, null, "FR");
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "pic.jpg", "FR")
        );
        Page<UserEntity> page = new PageImpl<>(users, PageRequest.of(0, 20), 1);
        when(filterUsersUseCase.execute(filter, PageRequest.of(0, 20))).thenReturn(page);

        ResponseEntity<Page<UserEntity>> response = userHandler.filterUsers(Gender.MALE, null, null, null, null, null, "FR", 0, 20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(users, response.getBody().getContent());
        verify(filterUsersUseCase, times(1)).execute(filter, PageRequest.of(0, 20));
    }

    @Test
    @DisplayName("Should return 200 and empty page when no users match filter")
    void shouldReturnOkWithEmptyListWhenNoUsersMatchFilter() {
        UserFilter filter = new UserFilter(null, "NonExistent", null, null, null, null, null);
        Page<UserEntity> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 20), 0);
        when(filterUsersUseCase.execute(filter, PageRequest.of(0, 20))).thenReturn(emptyPage);

        ResponseEntity<Page<UserEntity>> response = userHandler.filterUsers(null, "NonExistent", null, null, null, null, null, 0, 20);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(filterUsersUseCase, times(1)).execute(filter, PageRequest.of(0, 20));
    }
}
