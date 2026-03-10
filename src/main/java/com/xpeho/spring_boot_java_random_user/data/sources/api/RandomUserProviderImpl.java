package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResponse;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.RandomUserProvider;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class RandomUserProviderImpl implements RandomUserProvider {
    private final RandomUserApi randomUserApi;
    private final UserConverter userConverter;

    public RandomUserProviderImpl(RandomUserApi randomUserApi, UserConverter userConverter) {
        this.randomUserApi = randomUserApi;
        this.userConverter = userConverter;
    }

    @Override
    public PaginatedUsers fetchRandomUsers(int page, int size) throws IOException {
        // Convert 1-based page index to 0-based skip offset
        int skip = (page - 1) * size;
        Response<RandomUserResponse> response = randomUserApi.getRandomUsers(size, skip).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to fetch users: " + response.code());
        }
        RandomUserResponse body = response.body();
        List<RandomUserResultDAO> users = body.getUsers();
        if (users == null) {
            throw new IOException("Failed to parse users from response");
        }
        List<UserEntity> entities = users.stream()
                .map(userConverter::fromApiModel)
                .toList();
        return new PaginatedUsers(entities, body.getTotal(), body.getSkip(), body.getLimit());
    }
}
