package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final LocalUserService localUserService;
    private final RemoteUserService remoteUserService;

    public FetchAndSaveRandomUsersUseCase(LocalUserService localUserService, RemoteUserService remoteUserService) {
        this.localUserService = localUserService;
        this.remoteUserService = remoteUserService;
    }

    public PaginatedUsers execute(int page, int size) throws IOException {
        PaginatedUsers response = remoteUserService.fetchUsers(page, size);
        localUserService.saveAll(response.data());
        return response;
    }
}
