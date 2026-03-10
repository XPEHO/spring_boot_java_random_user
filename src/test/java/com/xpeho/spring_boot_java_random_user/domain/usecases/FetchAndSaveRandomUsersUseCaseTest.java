package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.RandomUserProvider;
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
    private RandomUserProvider randomUserProvider;
    private FetchAndSaveRandomUsersUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        randomUserProvider = mock(RandomUserProvider.class);
        useCase = new FetchAndSaveRandomUsersUseCase(userService, randomUserProvider);
    }

    @Test
    @DisplayName("Should fetch users from API and save them")
    void shouldFetchUsersFromApiAndSaveThem() throws IOException {
        int page = 1;
        int size = 10;
        int total = 50;
        int skip = 0;
        int limit = 10;
        List<UserEntity> fetched = List.of(new UserEntity(
                1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR"
        ));
        PaginatedUsers paginatedUsers = new PaginatedUsers(fetched, total, skip, limit);
        when(randomUserProvider.fetchRandomUsers(page, size)).thenReturn(paginatedUsers);
        when(userService.saveAll(fetched)).thenReturn(fetched);

        PaginatedUsers result = useCase.execute(page, size);

        assertEquals(paginatedUsers, result);
        assertEquals(fetched, result.data());
        assertEquals(total, result.total());
        assertEquals(skip, result.skip());
        assertEquals(limit, result.limit());
        verify(randomUserProvider).fetchRandomUsers(page, size);
        verify(userService).saveAll(fetched);
    }

    @Test
    @DisplayName("Should propagate IOException when API fails")
    void shouldPropagateIOExceptionWhenApiFails() throws IOException {
        int page = 1;
        int size = 10;
        when(randomUserProvider.fetchRandomUsers(page, size)).thenThrow(new IOException("API error"));
        IOException ex = assertThrows(IOException.class, () -> useCase.execute(page, size));
        assertEquals("API error", ex.getMessage());
    }

    @Test
    @DisplayName("Should handle empty users list gracefully")
    void shouldHandleEmptyUsersListGracefully() throws IOException {
        int page = 1;
        int size = 10;
        int total = 0;
        PaginatedUsers paginatedUsers = new PaginatedUsers(List.of(), total, 0, 10);
        when(randomUserProvider.fetchRandomUsers(page, size)).thenReturn(paginatedUsers);
        when(userService.saveAll(List.of())).thenReturn(List.of());

        PaginatedUsers result = useCase.execute(page, size);

        assertEquals(paginatedUsers, result);
        assertTrue(result.data().isEmpty());
        assertEquals(total, result.total());
    }
}
