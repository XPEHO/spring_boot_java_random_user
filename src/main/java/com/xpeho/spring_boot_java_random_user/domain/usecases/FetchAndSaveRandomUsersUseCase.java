package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FetchAndSaveRandomUsersUseCase {

    private final UserService userService;
    private final Map<UserSource, RemoteUserService> remoteUserServices;

    public FetchAndSaveRandomUsersUseCase(UserService userService, List<RemoteUserService> remoteUserServices) {
        this.userService = userService;
        this.remoteUserServices = remoteUserServices.stream()
                .collect(Collectors.toMap(RemoteUserService::getSource, Function.identity()));
    }

    public PaginatedUsers execute(int page, int size, UserSource source) throws IOException {
        RemoteUserService remoteUserService = remoteUserServices.get(source);
        if (remoteUserService == null) {
            throw new IllegalStateException("No remote service configured for source: " + source);
        }
        PaginatedUsers response = remoteUserService.fetchUsers(page, size);
        userService.saveAll(response.data());
        return response;
    }
}
