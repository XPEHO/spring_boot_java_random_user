package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.exceptions.UserNotFoundException;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UpdateRandomUserUseCase {
    private final UserService userService;

    public UpdateRandomUserUseCase(UserService userService) {
        this.userService = userService;
    }

    public UserEntity execute(long id, UserEntity newData) {
        UserEntity existingUser = userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserEntity updatedUser = new UserEntity(
                existingUser.id(),
                newData.gender(),
                newData.firstname(),
                newData.lastname(),
                newData.civility(),
                newData.email(),
                newData.phone(),
                newData.picture(),
                newData.nat()
        );

        return userService.save(updatedUser);
    }
}
