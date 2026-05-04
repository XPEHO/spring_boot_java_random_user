package com.xpeho.spring_boot_java_random_user.presentation.handlers;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import com.xpeho.spring_boot_java_random_user.presentation.controllers.UserController;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserRequestDTO;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserResponseDTO;
import com.xpeho.spring_boot_java_random_user.presentation.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Validated
@RestController
public class UserHandler implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    private static final String USER_NOT_FOUND_LOG = "warning: the requested user does not exist: {}";

    private final FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;
    private final UpdateRandomUserUseCase updateRandomUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserByIdUseCase deleteUserUseCase;
    private final FilterUsersUseCase filterUsersUseCase;

    public UserHandler(
            FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase,
            UpdateRandomUserUseCase updateRandomUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            CreateUserUseCase createUserUseCase,
            DeleteUserByIdUseCase deleteUserUseCase,
            FilterUsersUseCase filterUsersUseCase
    ) {
        this.fetchAndSaveRandomUsersUseCase = fetchAndSaveRandomUsersUseCase;
        this.updateRandomUserUseCase = updateRandomUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.filterUsersUseCase = filterUsersUseCase;
    }

    @Override
    public ResponseEntity<UserResponseDTO> getRandomUsers(Pageable pageable, UserSource source) {
        try {
            int page = pageable.getPageNumber() + 1; // 0-based → 1-based
            int size = pageable.getPageSize();
            PaginatedUsers result = fetchAndSaveRandomUsersUseCase.execute(page, size, source);
            UserResponseDTO response = new UserResponseDTO(
                    result.data().stream().map(UserMapper::toDTO).toList(),
                    result.total(),
                    result.skip(),
                    result.limit()
            );
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("Error fetching random users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> updateRandomUser(int id, UserRequestDTO user) {
        try {
            UserEntity input = toUserEntity(user);
            UserEntity savedUser = updateRandomUserUseCase.execute(id, input);
            return ResponseEntity.ok(UserMapper.toDTO(savedUser));
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(int id) {
        try {
            UserEntity user = getUserByIdUseCase.execute(id);
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestDTO user) {
        UserEntity input = toUserEntity(user);
        UserEntity createdUser = createUserUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(createdUser));
    }

    @Override
    public ResponseEntity<UserResponseDTO> filterUsers(
            Gender gender, String firstname, String lastname,
            String civility, String email, String phone, String nat,
            Pageable pageable
    ) {
        UserFilter filter = new UserFilter(gender, firstname, lastname, civility, email, phone, nat);
        int page = pageable.getPageNumber() + 1;
        int size = pageable.getPageSize();
        PaginatedUsers result = filterUsersUseCase.execute(filter, page, size);
        UserResponseDTO response = new UserResponseDTO(
                result.data().stream().map(UserMapper::toDTO).toList(),
                result.total(),
                result.skip(),
                result.limit()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public void deleteUserById(int id) {
        try {
            deleteUserUseCase.execute(id);
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
        }
    }

    private UserEntity toUserEntity(UserRequestDTO dto) {
        return new UserEntity(null, dto.gender(), dto.firstname(), dto.lastname(),
                dto.civility(), dto.email(), dto.phone(), dto.picture(), dto.nat());
    }

    private void logUserNotFound(UserNotFoundException e) {
        logger.warn(USER_NOT_FOUND_LOG, e.getMessage());
    }
}
