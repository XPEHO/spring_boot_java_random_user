package com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResponseDTO;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResultDTO;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class RandomUserServiceImpl implements RemoteUserService {
    private final RandomUserApi randomUserApi;
    private final UserConverter userConverter;

    public RandomUserServiceImpl(RandomUserApi randomUserApi, UserConverter userConverter) {
        this.randomUserApi = randomUserApi;
        this.userConverter = userConverter;
    }

    @Override
    public UserSource getSource() {
        return UserSource.RANDOM_USER;
    }

    @Override
    public PaginatedUsers fetchUsers(int page, int size) throws IOException {
        Response<RandomUserResponseDTO> response = randomUserApi.getUsers(size, page).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Failed to fetch users from randomuser.me: " + response.code());
        }
        RandomUserResponseDTO body = response.body();
        List<RandomUserResultDTO> users = body.getResults();
        if (users == null) {
            throw new IOException("Failed to parse users from randomuser.me response");
        }
        List<UserEntity> entities = users.stream()
                .map(userConverter::fromRandomUserApiModel)
                .toList();

        int skip = (page - 1) * size;
        return new PaginatedUsers(entities, entities.size(), skip, size);
    }
}

