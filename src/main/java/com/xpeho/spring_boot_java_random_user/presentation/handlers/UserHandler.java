package com.xpeho.spring_boot_java_random_user.presentation.handlers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import com.xpeho.spring_boot_java_random_user.presentation.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;

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
    public ResponseEntity<List<UserEntity>> getRandomUsers(int count) {
        try {
            List<UserEntity> users = fetchAndSaveRandomUsersUseCase.execute(count);
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            logger.error("Error fetching random users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(emptyList());
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
