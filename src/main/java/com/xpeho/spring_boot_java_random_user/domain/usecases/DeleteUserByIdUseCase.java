package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserByIdUseCase {
    private final UserService userService;

    public DeleteUserByIdUseCase(UserService userService) {
        this.userService = userService;
    }

    public void execute(long id) {
        userService.deleteById(id);
    }
}
