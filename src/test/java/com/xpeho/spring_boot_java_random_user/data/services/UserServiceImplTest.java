package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    private UserRepository userRepository;
    private UserConverter userConverter;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userConverter = mock(UserConverter.class);
        userService = new UserServiceImpl(userRepository, userConverter);
    }

    @Test
    @DisplayName("Should return mapped user when id exists")
    void shouldReturnMappedUserWhenIdExists() {
        User dao = new User();
        dao.setId(1L);
        dao.setFirstname("John");

        UserEntity expected = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR");

        when(userRepository.findById(1L)).thenReturn(Optional.of(dao));
        when(userConverter.toDomain(dao)).thenReturn(expected);

        Optional<UserEntity> result = userService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(userRepository).findById(1L);
        verify(userConverter).toDomain(dao);
    }

    @Test
    @DisplayName("Should return empty optional when id does not exist")
    void shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.getById(2L);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(2L);
    }

    @Test
    @DisplayName("Should save mapped user and return mapped domain entity")
    void shouldSaveMappedUserAndReturnMappedDomainEntity() {
        UserEntity input = new UserEntity(3L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US");

        User daoToSave = new User();
        daoToSave.setFirstname("Alice");

        User savedDao = new User();
        savedDao.setId(3L);
        savedDao.setFirstname("Alice");

        UserEntity expected = new UserEntity(3L, "female", "Alice", "Smith", "Mrs", "alice@smith.com", "5678", "new-pic.jpg", "US");

        when(userConverter.toDao(input)).thenReturn(daoToSave);
        when(userRepository.save(daoToSave)).thenReturn(savedDao);
        when(userConverter.toDomain(savedDao)).thenReturn(expected);

        UserEntity result = userService.save(input);

        assertEquals(expected, result);
        verify(userConverter).toDao(input);
        verify(userRepository).save(daoToSave);
        verify(userConverter).toDomain(savedDao);
    }

    @Test
    @DisplayName("Should build a specification and call repository for filtered users")
    void shouldFilterUsersWithGender() {
        UserFilter filter = new UserFilter(Gender.MALE, "John", null, null, null, null, null);

        User dao = new User();
        dao.setId(1L);
        dao.setFirstname("John");

        UserEntity expected = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR");

        when(userRepository.findAll(org.mockito.ArgumentMatchers.<Specification<User>>any()))
                .thenReturn(List.of(dao));
        when(userConverter.toDomain(dao)).thenReturn(expected);

        List<UserEntity> result = userService.filterUsers(filter);

        assertEquals(1, result.size());
        assertEquals(expected, result.get(0));
        verify(userRepository).findAll(org.mockito.ArgumentMatchers.<Specification<User>>any());
        verify(userConverter).toDomain(dao);
    }

    @Test
    @DisplayName("Should call repository when gender filter is null")
    void shouldFilterUsersWithNullGender() {
        UserFilter filter = new UserFilter(null, null, "Smith", null, null, null, null);

        User dao = new User();
        dao.setId(2L);
        dao.setLastname("Smith");

        UserEntity expected = new UserEntity(2L, "female", "Alice", "Smith", "Ms", "alice@smith.com", "5678", "pic2.jpg", "US");

        when(userRepository.findAll(org.mockito.ArgumentMatchers.<Specification<User>>any()))
                .thenReturn(List.of(dao));
        when(userConverter.toDomain(dao)).thenReturn(expected);

        List<UserEntity> result = userService.filterUsers(filter);

        assertEquals(1, result.size());
        assertEquals(expected, result.get(0));
        verify(userRepository).findAll(org.mockito.ArgumentMatchers.<Specification<User>>any());
    }

    @Test
    @DisplayName("Should return empty list when no users match filter")
    void shouldReturnEmptyListWhenNoUsersMatchFilter() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Unknown", null, null, null, null, null);

        when(userRepository.findAll(org.mockito.ArgumentMatchers.<Specification<User>>any()))
                .thenReturn(Collections.emptyList());

        List<UserEntity> result = userService.filterUsers(filter);

        assertTrue(result.isEmpty());
        verify(userRepository).findAll(org.mockito.ArgumentMatchers.<Specification<User>>any());
    }
}
