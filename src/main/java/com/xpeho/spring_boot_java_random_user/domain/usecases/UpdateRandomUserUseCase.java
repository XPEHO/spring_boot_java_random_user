package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UpdateRandomUserUseCase {
    private final UserService userService;

    public UpdateRandomUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public UserEntity execute(int id, UserRequest user) {
        UserEntity existingUser = userService.getById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        UserEntity updatedUser = new UserEntity(
            existingUser.id(),
            user.gender(),
            user.firstname(),
            user.lastname(),
            user.civility(),
            user.email(),
            user.phone(),
            user.picture(),
            user.nat()
        );

        return userService.save(updatedUser);
    }
}
