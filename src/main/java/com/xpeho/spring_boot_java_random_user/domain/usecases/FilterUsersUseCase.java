package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterUsersUseCase {
    private final LocalUserService userService;

    public FilterUsersUseCase(LocalUserService userService) {
        this.userService = userService;
    }

    public List<UserEntity> execute(UserFilter filter) {
        return userService.filterUsers(filter);
    }
}
