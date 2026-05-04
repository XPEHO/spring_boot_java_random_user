package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;

public class FilterUsersUseCase {
    private final UserService userService;

    public FilterUsersUseCase(UserService userService) {
        this.userService = userService;
    }

    public PaginatedUsers execute(UserFilter filter, int page, int size) {
        return userService.filterUsers(filter, page, size);
    }
}
