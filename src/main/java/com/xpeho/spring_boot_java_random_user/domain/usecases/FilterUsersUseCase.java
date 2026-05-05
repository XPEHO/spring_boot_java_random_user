package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilterUsersUseCase {
    private final UserService userService;

    public FilterUsersUseCase(UserService userService) {
        this.userService = userService;
    }

    public Page<UserEntity> execute(UserFilter filter, Pageable pageable) {
        return userService.filterUsers(filter, pageable);
    }
}
