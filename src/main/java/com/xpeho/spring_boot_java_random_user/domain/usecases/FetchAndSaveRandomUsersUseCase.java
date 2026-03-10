package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.services.RandomUserProvider;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final UserService userService;
    private final RandomUserProvider randomUserProvider;

    public FetchAndSaveRandomUsersUseCase(UserService userService, RandomUserProvider randomUserProvider) {
        this.userService = userService;
        this.randomUserProvider = randomUserProvider;
    }

    public PaginatedUsers execute(int page, int size) throws IOException {
        PaginatedUsers response = randomUserProvider.fetchRandomUsers(page, size);
        userService.saveAll(response.data());
        return response;
    }
}
