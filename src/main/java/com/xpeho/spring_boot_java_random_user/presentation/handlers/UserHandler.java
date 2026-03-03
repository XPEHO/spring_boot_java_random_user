package com.xpeho.spring_boot_java_random_user.presentation.handlers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.usecases.FetchAndSaveRandomUsersUseCase;
import com.xpeho.spring_boot_java_random_user.presentation.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;

@RestController
public class UserHandler implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    
    private final FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;

    public UserHandler(FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase) {
        this.fetchAndSaveRandomUsersUseCase = fetchAndSaveRandomUsersUseCase;
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
}