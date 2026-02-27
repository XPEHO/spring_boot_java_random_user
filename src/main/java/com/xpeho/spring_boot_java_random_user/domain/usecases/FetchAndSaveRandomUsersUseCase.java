package com.xpeho.spring_boot_java_random_user.domain.usecases;

import com.xpeho.spring_boot_java_random_user.data.models.RandomUserResponseDAO;
import com.xpeho.spring_boot_java_random_user.data.sources.RandomUserRetrofitClient;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@Service
public class FetchAndSaveRandomUsersUseCase {

    private final UserService userService;
    private final UserConverter userConverter;
    private final RandomUserRetrofitClient randomUserRetrofitClient;

    public FetchAndSaveRandomUsersUseCase(UserService userService, UserConverter userConverter, RandomUserRetrofitClient randomUserRetrofitClient) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.randomUserRetrofitClient = randomUserRetrofitClient;
    }

    public List<UserEntity> execute(int count) throws IOException {
        Response<RandomUserResponseDAO> response = randomUserRetrofitClient.getApi()
                .getRandomUsers(count)
                .execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to fetch users: " + response.code());
        }

        // Utilisation des Streams pour la transformation
        List<UserEntity> users = Arrays.stream(response.body().results)
            .map(userConverter::modelToEntity)
            .toList();

        return userService.saveAll(users);
    }
}