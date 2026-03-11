package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResponse;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class DummyUserServiceImpl implements RemoteUserService {
    private final DummyUserApi dummyUserApi;
    private final UserConverter userConverter;

    public DummyUserServiceImpl(DummyUserApi dummyUserApi, UserConverter userConverter) {
        this.dummyUserApi = dummyUserApi;
        this.userConverter = userConverter;
    }

    @Override
    public PaginatedUsers fetchUsers(int page, int size) throws IOException {
        // Convert 1-based page index to 0-based skip offset
        int skip = (page - 1) * size;
        Response<DummyUserResponse> response = dummyUserApi.getUsers(size, skip).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to fetch users: " + response.code());
        }
        DummyUserResponse body = response.body();
        List<DummyUserResultDTO> users = body.getUsers();
        if (users == null) {
            throw new IOException("Failed to parse users from response");
        }
        List<UserEntity> entities = users.stream()
                .map(userConverter::fromApiModel)
                .toList();
        return new PaginatedUsers(entities, body.getTotal(), body.getSkip(), body.getLimit());
    }
}

