package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchAndSaveRandomUsersUseCaseTest {
    private UserService userService;
    private FetchAndSaveRandomUsersUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new FetchAndSaveRandomUsersUseCase(userService);
    }

    @Test
    @DisplayName("Should fetch users from API and save them")
    void shouldFetchUsersFromApiAndSaveThem() throws IOException {
        int page = 1;
        int size = 10;
        List<UserEntity> fetched = List.of(new UserEntity(
                1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR"
        ));
        PaginatedUsers paginatedUsers = new PaginatedUsers(fetched, 50, 0, 10);
        when(userService.fetchAndSaveUsers(page, size, UserSource.DUMMY)).thenReturn(paginatedUsers);

        PaginatedUsers result = useCase.execute(page, size, UserSource.DUMMY);

        assertEquals(paginatedUsers, result);
        assertEquals(fetched, result.data());
        assertEquals(50, result.total());
        assertEquals(0, result.skip());
        assertEquals(10, result.limit());
        verify(userService).fetchAndSaveUsers(page, size, UserSource.DUMMY);
    }

    @Test
    @DisplayName("Should propagate IOException when API fails")
    void shouldPropagateIOExceptionWhenApiFails() throws IOException {
        int page = 1;
        int size = 10;
        when(userService.fetchAndSaveUsers(page, size, UserSource.RANDOM_USER)).thenThrow(new IOException("API error"));

        IOException ex = assertThrows(IOException.class, () -> useCase.execute(page, size, UserSource.RANDOM_USER));
        assertEquals("API error", ex.getMessage());
    }

    @Test
    @DisplayName("Should handle empty users list gracefully")
    void shouldHandleEmptyUsersListGracefully() throws IOException {
        int page = 1;
        int size = 10;
        PaginatedUsers paginatedUsers = new PaginatedUsers(List.of(), 0, 0, 10);
        when(userService.fetchAndSaveUsers(page, size, UserSource.DUMMY)).thenReturn(paginatedUsers);

        PaginatedUsers result = useCase.execute(page, size, UserSource.DUMMY);

        assertEquals(paginatedUsers, result);
        assertTrue(result.data().isEmpty());
        assertEquals(0, result.total());
    }
}
