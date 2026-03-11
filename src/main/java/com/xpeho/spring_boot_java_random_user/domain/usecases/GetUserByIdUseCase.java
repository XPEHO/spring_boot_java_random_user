package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCase {
    private final LocalUserService userService;

    public GetUserByIdUseCase(LocalUserService userService) {
        this.userService = userService;
    }

    public UserEntity execute(long id) {
        return userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
