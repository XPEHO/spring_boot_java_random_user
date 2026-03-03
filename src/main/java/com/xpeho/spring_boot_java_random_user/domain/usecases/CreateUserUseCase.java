package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserService userService;

    public CreateUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public UserEntity execute(UserRequest user) {
        UserEntity userToCreate = new UserEntity(
        null,
            user.gender(),
            user.firstname(),
            user.lastname(),
            user.civility(),
            user.email(),
            user.phone(),
            user.picture(),
            user.nat()
        );

        return userService.save(userToCreate);
    }
}
