package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.RandomUserProvider;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
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
    void testSuccessfulApiResponse() throws IOException {
        List<UserEntity> fetched = List.of(new UserEntity(
                1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR"
        ));        when(randomUserProvider.fetchRandomUsers(2)).thenReturn(fetched);
        when(userService.saveAll(fetched)).thenReturn(fetched);
        List<UserEntity> result = useCase.execute(2);
        assertEquals(fetched, result);
        verify(randomUserProvider).fetchRandomUsers(2);
        verify(userService).saveAll(fetched);
    }

    @Test
    void testApiThrowsIOException() throws IOException {
        when(randomUserProvider.fetchRandomUsers(1)).thenThrow(new IOException("API error"));
        IOException ex = assertThrows(IOException.class, () -> useCase.execute(1));
        assertEquals("API error", ex.getMessage());
    }

    @Test
    void testNullResponse() throws IOException {
        when(randomUserProvider.fetchRandomUsers(1)).thenReturn(null);
        when(userService.saveAll(null)).thenReturn(null);
        List<UserEntity> result = useCase.execute(1);
        assertNull(result);
    }

    @Test
    void testEmptyResultArray() throws IOException {
        when(randomUserProvider.fetchRandomUsers(0)).thenReturn(Collections.emptyList());
        when(userService.saveAll(Collections.emptyList())).thenReturn(Collections.emptyList());
        List<UserEntity> result = useCase.execute(0);
        assertTrue(result.isEmpty());
    }
}
