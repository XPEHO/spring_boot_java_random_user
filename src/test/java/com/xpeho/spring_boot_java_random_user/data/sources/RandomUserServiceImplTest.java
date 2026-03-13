package com.xpeho.spring_boot_java_random_user.data.sources;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResponseDTO;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser.RandomUserApi;
import com.xpeho.spring_boot_java_random_user.data.sources.api.randomuser.RandomUserServiceImpl;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RandomUserServiceImplTest {
    private RandomUserApi randomUserApi;
    private UserConverter userConverter;
    private RandomUserServiceImpl service;
    private Call<RandomUserResponseDTO> call;

    @BeforeEach
    void setUp() {
        randomUserApi = mock(RandomUserApi.class);
        userConverter = mock(UserConverter.class);
        //noinspection unchecked
        call = (Call<RandomUserResponseDTO>) mock(Call.class);
        service = new RandomUserServiceImpl(randomUserApi, userConverter);
    }

    @Test
    @DisplayName("Should expose RANDOM_USER source")
    void shouldExposeRandomUserSource() {
        assertEquals(UserSource.RANDOM_USER, service.getSource());
    }

    @Test
    @DisplayName("Should fetch and convert users successfully")
    void shouldFetchAndConvertUsersSuccessfully() throws IOException {
        int page = 2;
        int size = 2;
        int skip = 2;

        RandomUserResponseDTO responseObj = new RandomUserResponseDTO();
        RandomUserResultDTO dto1 = new RandomUserResultDTO();
        RandomUserResultDTO dto2 = new RandomUserResultDTO();
        responseObj.setResults(List.of(dto1, dto2));

        UserEntity entity1 = new UserEntity(null, "a", "b", "c", "d", "e", "f", "g", "h");
        UserEntity entity2 = new UserEntity(null, "i", "j", "k", "l", "m", "n", "o", "p");

        when(randomUserApi.getUsers(size, page)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseObj));
        when(userConverter.fromRandomUserApiModel(dto1)).thenReturn(entity1);
        when(userConverter.fromRandomUserApiModel(dto2)).thenReturn(entity2);

        PaginatedUsers result = service.fetchUsers(page, size);

        assertEquals(2, result.data().size());
        assertEquals(entity1, result.data().get(0));
        assertEquals(entity2, result.data().get(1));
        assertEquals(2, result.total());
        assertEquals(skip, result.skip());
        assertEquals(size, result.limit());
    }

    @Test
    @DisplayName("Should throw IOException when API returns error")
    void shouldThrowIOExceptionWhenApiReturnsError() throws IOException {
        int page = 1;
        int size = 1;

        when(randomUserApi.getUsers(size, page)).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "error")));

        assertThrows(IOException.class, () -> service.fetchUsers(page, size));
    }

    @Test
    @DisplayName("Should throw IOException when response body is null")
    void shouldThrowIOExceptionWhenResponseBodyIsNull() throws IOException {
        int page = 1;
        int size = 1;

        when(randomUserApi.getUsers(size, page)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(null));

        assertThrows(IOException.class, () -> service.fetchUsers(page, size));
    }
}

