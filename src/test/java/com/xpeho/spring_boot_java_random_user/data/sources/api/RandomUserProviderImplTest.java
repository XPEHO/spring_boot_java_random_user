package com.xpeho.spring_boot_java_random_user.data.sources.api;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResponse;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomUserProviderImplTest {
    private RandomUserApi randomUserApi;
    private UserConverter userConverter;
    private RandomUserProviderImpl provider;
    private Call<RandomUserResponse> call;

    @BeforeEach
    void setUp() {
        randomUserApi = mock(RandomUserApi.class);
        userConverter = mock(UserConverter.class);
        call = mock(Call.class);
        provider = new RandomUserProviderImpl(randomUserApi, userConverter);
    }

    @Test
    @DisplayName("Should fetch and convert users successfully")
    void shouldFetchAndConvertUsersSuccessfully() throws IOException {
        int page = 1;
        int size = 2;
        int total = 100;
        int skip = 0;
        int limit = 2;
        RandomUserResponse responseObj = new RandomUserResponse();
        RandomUserResultDAO dao1 = new RandomUserResultDAO();
        RandomUserResultDAO dao2 = new RandomUserResultDAO();
        responseObj.setUsers(List.of(dao1, dao2));
        responseObj.setTotal(total);
        responseObj.setSkip(skip);
        responseObj.setLimit(limit);
        UserEntity entity1 = new UserEntity(null, "a", "b", "c", "d", "e", "f", "g", "h");
        UserEntity entity2 = new UserEntity(null, "i", "j", "k", "l", "m", "n", "o", "p");
        when(randomUserApi.getRandomUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseObj));
        when(userConverter.fromApiModel(dao1)).thenReturn(entity1);
        when(userConverter.fromApiModel(dao2)).thenReturn(entity2);

        PaginatedUsers result = provider.fetchRandomUsers(page, size);
        assertEquals(2, result.data().size());
        assertEquals(entity1, result.data().get(0));
        assertEquals(entity2, result.data().get(1));
        assertEquals(total, result.total());
        assertEquals(skip, result.skip());
        assertEquals(limit, result.limit());
    }

    @Test
    @DisplayName("Should throw IOException when API returns error")
    void shouldThrowIOExceptionWhenApiReturnsError() throws IOException {
        int page = 1;
        int size = 1;
        int skip = 0;
        when(randomUserApi.getRandomUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "error")));
        assertThrows(IOException.class, () -> provider.fetchRandomUsers(page, size));
    }

    @Test
    @DisplayName("Should throw IOException when response body is null")
    void shouldThrowIOExceptionWhenResponseBodyIsNull() throws IOException {
        int page = 1;
        int size = 1;
        int skip = 0;
        when(randomUserApi.getRandomUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(null));
        assertThrows(IOException.class, () -> provider.fetchRandomUsers(page, size));
    }
}
