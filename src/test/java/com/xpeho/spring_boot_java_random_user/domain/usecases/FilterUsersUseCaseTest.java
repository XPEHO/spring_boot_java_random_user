package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterUsersUseCaseTest {
    private UserService userService;
    private FilterUsersUseCase useCase;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        useCase = new FilterUsersUseCase(userService);
        pageable = PageRequest.of(0, 20);
    }

    @Test
    @DisplayName("Should return filtered users matching the filter")
    void shouldReturnFilteredUsers() {
        UserFilter filter = new UserFilter(Gender.MALE, "John", null, null, null, null, null);
        List<UserEntity> content = List.of(
                new UserEntity(1L, "male", "John", "Doe", "Mr", "john@example.com", "0600000000", "http://pic.jpg", "FR")
        );
        Page<UserEntity> expected = new PageImpl<>(content, pageable, 1);
        when(userService.filterUsers(filter, pageable)).thenReturn(expected);

        Page<UserEntity> result = useCase.execute(filter, pageable);

        assertEquals(expected, result);
        verify(userService).filterUsers(filter, pageable);
    }

    @Test
    @DisplayName("Should return empty page when no users match the filter")
    void shouldReturnEmptyListWhenNoMatch() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Unknown", null, null, null, null, null);
        Page<UserEntity> empty = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userService.filterUsers(filter, pageable)).thenReturn(empty);

        Page<UserEntity> result = useCase.execute(filter, pageable);

        assertTrue(result.isEmpty());
        verify(userService).filterUsers(filter, pageable);
    }

    @Test
    @DisplayName("Should pass filter with all fields to the service")
    void shouldPassFilterWithAllFields() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "US");
        List<UserEntity> content = List.of(
                new UserEntity(5L, "female", "Alice", "Smith", "Ms", "alice@example.com", "0611111111", "http://pic2.jpg", "US")
        );
        Page<UserEntity> expected = new PageImpl<>(content, pageable, 1);
        when(userService.filterUsers(filter, pageable)).thenReturn(expected);

        Page<UserEntity> result = useCase.execute(filter, pageable);

        assertEquals(expected, result);
        verify(userService, times(1)).filterUsers(filter, pageable);
        verifyNoMoreInteractions(userService);
    }
}
