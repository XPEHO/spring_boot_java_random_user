package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final UserService userService;

    public FetchAndSaveRandomUsersUseCase(UserService userService) {
        this.userService = userService;
    }

    public PaginatedUsers execute(int page, int size, UserSource source) throws IOException {
        return userService.fetchAndSaveUsers(page, size, source);
    }
}
