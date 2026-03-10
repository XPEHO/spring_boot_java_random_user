package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResponse;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
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
    public List<UserEntity> fetchRandomUsers(int count) throws IOException {
        Response<DummyUserResponse> response = randomUserApi.getRandomUsers(count).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to fetch users: " + response.code());
        }
        List<DummyUserResultDTO> users = response.body().getUsers();
        if (users == null) {
            throw new IOException("Failed to parse users from response");
        }
        return users.stream()
                .map(userConverter::fromApiModel)
                .toList();
    }
}
