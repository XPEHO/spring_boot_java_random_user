package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterUsersUseCaseTest {
    private LocalUserService userService;
    private FilterUsersUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(LocalUserService.class);
        useCase = new FilterUsersUseCase(userService);
    }

    @Test
    @DisplayName("Should return filtered users matching the filter")
    void shouldReturnFilteredUsers() {
        UserFilter filter = new UserFilter(Gender.MALE, "John", null, null, null, null, null);
        List<UserEntity> expected = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "http://pic.jpg", "FR")
        );
        when(userService.filterUsers(filter)).thenReturn(expected);

        List<UserEntity> result = useCase.execute(filter);

        assertEquals(expected, result);
        verify(userService).filterUsers(filter);
    }

    @Test
    @DisplayName("Should return empty list when no users match the filter")
    void shouldReturnEmptyListWhenNoMatch() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Unknown", null, null, null, null, null);
        when(userService.filterUsers(filter)).thenReturn(Collections.emptyList());

        List<UserEntity> result = useCase.execute(filter);

        assertTrue(result.isEmpty());
        verify(userService).filterUsers(filter);
    }

    @Test
    @DisplayName("Should pass filter with all fields to the service")
    void shouldPassFilterWithAllFields() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "US");
        List<UserEntity> expected = List.of(
                new UserEntity(5L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "http://pic2.jpg", "US")
        );
        when(userService.filterUsers(filter)).thenReturn(expected);

        List<UserEntity> result = useCase.execute(filter);

        assertEquals(expected, result);
        verify(userService, times(1)).filterUsers(filter);
        verifyNoMoreInteractions(userService);
    }
}
