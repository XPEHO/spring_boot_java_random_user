package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final LocalUserService localUserService;
    private final Map<UserSource, RemoteUserService> remoteUserServices;

    public FetchAndSaveRandomUsersUseCase(LocalUserService localUserService, List<RemoteUserService> remoteUserServices) {
        this.localUserService = localUserService;
        this.remoteUserServices = remoteUserServices.stream()
                .collect(Collectors.toMap(RemoteUserService::getSource, Function.identity()));
    }

    public PaginatedUsers execute(int page, int size, UserSource source) throws IOException {
        RemoteUserService remoteUserService = remoteUserServices.get(source);
        if (remoteUserService == null) {
            throw new IOException("No remote service configured for source: " + source);
        }
        PaginatedUsers response = remoteUserService.fetchUsers(page, size);
        localUserService.saveAll(response.data());
        return response;
    }
}
