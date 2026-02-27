package com.xpeho.spring_boot_java_random_user.presentation.handlers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;
import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.domain.usecases.FetchAndSaveRandomUsersUseCase;
import com.xpeho.spring_boot_java_random_user.presentation.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@RestController
public class UserHandler implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    
    private final FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase;
    private final UserConverter userConverter;

    public UserHandler(FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase, UserConverter userConverter) {
        this.fetchAndSaveRandomUsersUseCase = fetchAndSaveRandomUsersUseCase;
        this.userConverter = userConverter;
    }

    @Override
    public ResponseEntity<List<UserDTO>> getRandomUsers(int count) {
            try {
                List<UserEntity> users = fetchAndSaveRandomUsersUseCase.execute(count);
                List<UserDTO> dtos = users.stream().map(userConverter::toDto).toList();
                return ResponseEntity.ok(dtos);
            } catch (IOException e) {
                logger.error("Error fetching random users: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(emptyList());
            }
    }
}