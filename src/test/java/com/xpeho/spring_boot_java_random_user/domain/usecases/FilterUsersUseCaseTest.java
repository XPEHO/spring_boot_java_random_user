package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterUsersUseCaseTest {
    private UserService userService;
    private FilterUsersUseCase useCase;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new FilterUsersUseCase(userService);
    }

    @Test
    @DisplayName("Should return paginated filtered users matching the filter")
    void shouldReturnFilteredUsers() {
        UserFilter filter = new UserFilter(Gender.MALE, "John", null, null, null, null, null);
        List<UserEntity> users = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "http://pic.jpg", "FR")
        );
        PaginatedUsers expected = new PaginatedUsers(users, 1, 0, 10);
        when(userService.filterUsers(filter, 1, 10)).thenReturn(expected);

        PaginatedUsers result = useCase.execute(filter, 1, 10);

        assertEquals(expected, result);
        verify(userService).filterUsers(filter, 1, 10);
    }

    @Test
    @DisplayName("Should return empty paginated result when no users match the filter")
    void shouldReturnEmptyListWhenNoMatch() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Unknown", null, null, null, null, null);
        PaginatedUsers empty = new PaginatedUsers(Collections.emptyList(), 0, 0, 10);
        when(userService.filterUsers(filter, 1, 10)).thenReturn(empty);

        PaginatedUsers result = useCase.execute(filter, 1, 10);

        assertTrue(result.data().isEmpty());
        verify(userService).filterUsers(filter, 1, 10);
    }

    @Test
    @DisplayName("Should pass filter with all fields to the service")
    void shouldPassFilterWithAllFields() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "US");
        List<UserEntity> users = List.of(
                new UserEntity(5L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "http://pic2.jpg", "US")
        );
        PaginatedUsers expected = new PaginatedUsers(users, 1, 0, 10);
        when(userService.filterUsers(filter, 1, 10)).thenReturn(expected);

        PaginatedUsers result = useCase.execute(filter, 1, 10);

        assertEquals(expected, result);
        verify(userService, times(1)).filterUsers(filter, 1, 10);
        verifyNoMoreInteractions(userService);
    }
}
