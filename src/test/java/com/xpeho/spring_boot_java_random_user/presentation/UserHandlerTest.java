package com.xpeho.spring_boot_java_random_user.presentation;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserRequestDTO;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserResponseDTO;
import com.xpeho.spring_boot_java_random_user.presentation.handlers.UserHandler;
import com.xpeho.spring_boot_java_random_user.presentation.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {

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
        Pageable pageable = PageRequest.of(0, 10);
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "pic.jpg", "FR")
        );
        PaginatedUsers paginatedUsers = new PaginatedUsers(users, 50, 0, 10);
        when(fetchAndSaveRandomUsersUseCase.execute(1, 10, UserSource.DUMMY)).thenReturn(paginatedUsers);

        ResponseEntity<UserResponseDTO> response = userHandler.getRandomUsers(pageable, UserSource.DUMMY);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(users.stream().map(UserMapper::toDTO).toList(), response.getBody().data());
        assertEquals(50, response.getBody().total());
        assertEquals(0, response.getBody().skip());
        assertEquals(10, response.getBody().limit());
        verify(fetchAndSaveRandomUsersUseCase, times(1)).execute(1, 10, UserSource.DUMMY);
    }

    @Test
    @DisplayName("Should return 500 when getRandomUsers throws IOException")
    void shouldReturnInternalServerErrorWhenGetRandomUsersFails() throws IOException {
        Pageable pageable = PageRequest.of(0, 10);
        when(fetchAndSaveRandomUsersUseCase.execute(1, 10, UserSource.RANDOM_USER)).thenThrow(new IOException("downstream unavailable"));

        ResponseEntity<UserResponseDTO> response = userHandler.getRandomUsers(pageable, UserSource.RANDOM_USER);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should return 200 and user when getUserById succeeds")
    void shouldReturnOkWhenGetUserByIdSucceeds() {
        UserEntity user = new UserEntity(42L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "alice.jpg", "US");
        when(getUserByIdUseCase.execute(42)).thenReturn(user);

        ResponseEntity<UserDTO> response = userHandler.getUserById(42);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserMapper.toDTO(user), response.getBody());
        verify(getUserByIdUseCase, times(1)).execute(42);
    }

    @Test
    @DisplayName("Should return 404 when getUserById throws UserNotFoundException")
    void shouldReturnNotFoundWhenGetUserByIdFails() {
        when(getUserByIdUseCase.execute(99)).thenThrow(new UserNotFoundException(99));

        ResponseEntity<UserDTO> response = userHandler.getUserById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should return 200 and updated user when updateRandomUser succeeds")
    void shouldReturnOkWhenUpdateRandomUserSucceeds() {
        UserRequestDTO request = new UserRequestDTO("female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        UserEntity input = new UserEntity(null, "female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        UserEntity updated = new UserEntity(7L, "female", "Jane", "Doe", "Ms", "jane@example.com", "0622222222", "jane.jpg", "FR");
        when(updateRandomUserUseCase.execute(7, input)).thenReturn(updated);

        ResponseEntity<UserDTO> response = userHandler.updateRandomUser(7, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserMapper.toDTO(updated), response.getBody());
        verify(updateRandomUserUseCase, times(1)).execute(7, input);
    }

    @Test
    @DisplayName("Should return 404 when updateRandomUser throws UserNotFoundException")
    void shouldReturnNotFoundWhenUpdateRandomUserFails() {
        UserRequestDTO request = new UserRequestDTO("male", "Bob", "Brown", "Mr", "bob@example.com", "0633333333", "bob.jpg", "DE");
        UserEntity input = new UserEntity(null, "male", "Bob", "Brown", "Mr", "bob@example.com", "0633333333", "bob.jpg", "DE");
        when(updateRandomUserUseCase.execute(123, input)).thenThrow(new UserNotFoundException(123));

        ResponseEntity<UserDTO> response = userHandler.updateRandomUser(123, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should return 201 and created user when createUser succeeds")
    void shouldReturnCreatedWhenCreateUserSucceeds() {
        UserRequestDTO request = new UserRequestDTO("female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        UserEntity input = new UserEntity(null, "female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        UserEntity created = new UserEntity(10L, "female", "Emma", "Stone", "Ms", "emma@example.com", "0644444444", "emma.jpg", "FR");
        when(createUserUseCase.execute(input)).thenReturn(created);

        ResponseEntity<UserDTO> response = userHandler.createUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(UserMapper.toDTO(created), response.getBody());
        verify(createUserUseCase, times(1)).execute(input);
    }

    @Test
    @DisplayName("Should return 204 when deleteUserById succeeds")
    void shouldReturnNoContentWhenDeleteUserByIdSucceeds() {
        userHandler.deleteUserById(42);
        verify(deleteUserUseCase, times(1)).execute(42);
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
    @DisplayName("Should return 200 and filtered users when filterUsers succeeds")
    void shouldReturnOkWhenFilterUsersSucceeds() {
        Pageable pageable = PageRequest.of(0, 10);
        UserFilter filter = new UserFilter(Gender.MALE, null, null, null, null, null, "FR");
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "pic.jpg", "FR")
        );
        PaginatedUsers paginatedUsers = new PaginatedUsers(users, 1, 0, 10);
        when(filterUsersUseCase.execute(filter, 1, 10)).thenReturn(paginatedUsers);

        ResponseEntity<UserResponseDTO> response = userHandler.filterUsers(Gender.MALE, null, null, null, null, null, "FR", pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        assertEquals(users.stream().map(UserMapper::toDTO).toList(), response.getBody().data());
        verify(filterUsersUseCase, times(1)).execute(filter, 1, 10);
    }

    @Test
    @DisplayName("Should return 200 and empty list when no users match filter")
    void shouldReturnOkWithEmptyListWhenNoUsersMatchFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        UserFilter filter = new UserFilter(null, "NonExistent", null, null, null, null, null);
        PaginatedUsers emptyResult = new PaginatedUsers(List.of(), 0, 0, 10);
        when(filterUsersUseCase.execute(filter, 1, 10)).thenReturn(emptyResult);

        ResponseEntity<UserResponseDTO> response = userHandler.filterUsers(null, "NonExistent", null, null, null, null, null, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().data().isEmpty());
    }
}
