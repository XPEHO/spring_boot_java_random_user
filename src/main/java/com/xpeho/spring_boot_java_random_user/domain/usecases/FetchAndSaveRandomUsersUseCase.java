package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.RandomUserProvider;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final UserService userService;
    private final RandomUserProvider randomUserProvider;

    public FetchAndSaveRandomUsersUseCase(UserService userService, RandomUserProvider randomUserProvider) {
        this.userService = userService;
        this.randomUserProvider = randomUserProvider;
    }

    public List<UserEntity> execute(int count) throws IOException {
        List<UserEntity> users = randomUserProvider.fetchRandomUsers(count);
        return userService.saveAll(users);
    }
}
