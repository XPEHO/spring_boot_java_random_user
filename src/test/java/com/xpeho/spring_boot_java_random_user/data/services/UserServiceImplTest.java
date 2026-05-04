package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.database.UserDao;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
        UserDao dao = new UserDao();
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
        UserDao daoToSave = new UserDao();
        daoToSave.setFirstname("Alice");
        UserDao savedDao = new UserDao();
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
    @DisplayName("Should return paginated users when filtering with gender")
    void shouldFilterUsersWithGender() {
        UserFilter filter = new UserFilter(Gender.MALE, "John", null, null, null, null, null);
        UserDao dao = new UserDao();
        dao.setId(1L);
        dao.setFirstname("John");
        UserEntity expected = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR");
        Page<UserDao> page = new PageImpl<>(List.of(dao));

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(userConverter.toDomain(dao)).thenReturn(expected);

        PaginatedUsers result = userService.filterUsers(filter, 1, 10);

        assertEquals(1, result.data().size());
        assertEquals(expected, result.data().get(0));
        assertEquals(1, result.total());
        verify(userRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(userConverter).toDomain(dao);
    }

    @Test
    @DisplayName("Should return paginated users when filtering with null gender")
    void shouldFilterUsersWithNullGender() {
        UserFilter filter = new UserFilter(null, null, "Smith", null, null, null, null);
        UserDao dao = new UserDao();
        dao.setId(2L);
        dao.setLastname("Smith");
        UserEntity expected = new UserEntity(2L, "female", "Alice", "Smith", "Ms", "alice@smith.com", "5678", "pic2.jpg", "US");
        Page<UserDao> page = new PageImpl<>(List.of(dao));

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(userConverter.toDomain(dao)).thenReturn(expected);

        PaginatedUsers result = userService.filterUsers(filter, 1, 10);

        assertEquals(1, result.data().size());
        verify(userRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Should return empty paginated result when no users match filter")
    void shouldReturnEmptyListWhenNoUsersMatchFilter() {
        UserFilter filter = new UserFilter(Gender.FEMALE, "Unknown", null, null, null, null, null);
        Page<UserDao> emptyPage = new PageImpl<>(Collections.emptyList());

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(emptyPage);

        PaginatedUsers result = userService.filterUsers(filter, 1, 10);

        assertTrue(result.data().isEmpty());
        assertEquals(0, result.total());
        verify(userRepository).findAll(any(Specification.class), any(Pageable.class));
    }
}
