package com.xpeho.spring_boot_java_random_user.presentation.handlers;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.InvalidPaginationException;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import com.xpeho.spring_boot_java_random_user.presentation.controllers.UserController;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class UserHandler implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    private static final String USER_NOT_FOUND_LOG = "warning: the requested user does not exist : {}";

    private final FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;
    private final UpdateRandomUserUseCase updateRandomUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserByIdUseCase deleteUserUseCase;

    public UserHandler(
            FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase,
            UpdateRandomUserUseCase updateRandomUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            CreateUserUseCase createUserUseCase,
            DeleteUserByIdUseCase deleteUserUseCase
    ) {
        this.fetchAndSaveRandomUsersUseCase = fetchAndSaveRandomUsersUseCase;
        this.updateRandomUserUseCase = updateRandomUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;

    }


    @Override
    public ResponseEntity<UserResponseDTO> getRandomUsers(int page, int size, UserSource source) {
        if (page < 1) {
            throw new InvalidPaginationException("Page must be greater than or equal to 1. Requested: " + page);
        }
        if (size < 1 || size > 30) {
            throw new InvalidPaginationException("Page size must be between 1 and 30. Requested: " + size);
        }
        try {
            PaginatedUsers result = fetchAndSaveRandomUsersUseCase.execute(page, size, source);
            UserResponseDTO response = new UserResponseDTO(
                    result.data(),
                    result.total(),
                    result.skip(),
                    result.limit()
            );
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("Error fetching random users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserEntity> updateRandomUser(int id, UserRequest user) {
        try {
            UserEntity savedUser = updateRandomUserUseCase.execute(id, user);
            return ResponseEntity.ok(savedUser);
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<UserEntity> getUserById(int id) {
        try {
            UserEntity user = getUserByIdUseCase.execute(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Override
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRequest user) {
        UserEntity createdUser = createUserUseCase.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    public void deleteUserById(int id) {
        try {
            deleteUserUseCase.execute(id);
        } catch (UserNotFoundException e) {
            logUserNotFound(e);
        }
    }

    private void logUserNotFound(UserNotFoundException e) {
        logger.warn(USER_NOT_FOUND_LOG, e.getMessage(), e);
    }
}
