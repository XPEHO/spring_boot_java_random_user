package com.xpeho.spring_boot_java_random_user.data.sources;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResponse;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.sources.api.dummy.DummyUserApi;
import com.xpeho.spring_boot_java_random_user.data.sources.api.dummy.DummyUserServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DummyUserServiceImplTest {
    private DummyUserApi dummyUserApi;
    private UserConverter userConverter;
    private DummyUserServiceImpl service;
    private Call<DummyUserResponse> call;

    @BeforeEach
    void setUp() {
        dummyUserApi = mock(DummyUserApi.class);
        userConverter = mock(UserConverter.class);
        //noinspection unchecked
        call = (Call<DummyUserResponse>) mock(Call.class);
        service = new DummyUserServiceImpl(dummyUserApi, userConverter);
    }

    @Test
    @DisplayName("Should expose DUMMY source")
    void shouldExposeDummySource() {
        assertEquals(UserSource.DUMMY, service.getSource());
    }

    @Test
    @DisplayName("Should fetch and convert users successfully")
    void shouldFetchAndConvertUsersSuccessfully() throws IOException {
        int page = 1;
        int size = 2;
        int total = 100;
        int skip = 0;
        int limit = 2;
        DummyUserResponse responseObj = new DummyUserResponse();
        DummyUserResultDTO dto1 = new DummyUserResultDTO();
        DummyUserResultDTO dto2 = new DummyUserResultDTO();
        responseObj.setUsers(List.of(dto1, dto2));
        responseObj.setTotal(total);
        responseObj.setSkip(skip);
        responseObj.setLimit(limit);
        UserEntity entity1 = new UserEntity(null, "a", "b", "c", "d", "e", "f", "g", "h");
        UserEntity entity2 = new UserEntity(null, "i", "j", "k", "l", "m", "n", "o", "p");
        when(dummyUserApi.getUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseObj));
        when(userConverter.fromApiModel(dto1)).thenReturn(entity1);
        when(userConverter.fromApiModel(dto2)).thenReturn(entity2);

        PaginatedUsers result = service.fetchUsers(page, size);
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
        when(dummyUserApi.getUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "error")));
        assertThrows(IOException.class, () -> service.fetchUsers(page, size));
    }

    @Test
    @DisplayName("Should throw IOException when response body is null")
    void shouldThrowIOExceptionWhenResponseBodyIsNull() throws IOException {
        int page = 1;
        int size = 1;
        int skip = 0;
        when(dummyUserApi.getUsers(size, skip)).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(null));
        assertThrows(IOException.class, () -> service.fetchUsers(page, size));
    }
}
