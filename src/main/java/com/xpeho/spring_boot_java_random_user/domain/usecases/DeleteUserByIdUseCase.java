package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserByIdUseCase {
    private final LocalUserService userService;

    public DeleteUserByIdUseCase(LocalUserService userService) {
        this.userService = userService;
    }

    public void execute(long id) {
        userService.deleteById(id);
    }
}
