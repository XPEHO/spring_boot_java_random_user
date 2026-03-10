package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DummyUserResponseTest {
    @Test
    @DisplayName("Should store and return users list")
    void shouldStoreAndReturnUsersList() {
        DummyUserResponse response = new DummyUserResponse();
        List<DummyUserResultDTO> users = List.of(new DummyUserResultDTO(), new DummyUserResultDTO());
        response.setUsers(users);
        assertEquals(users, response.getUsers());
    }

    @Test
    @DisplayName("Should have null users list by default")
    void shouldHaveNullUsersListByDefault() {
        DummyUserResponse response = new DummyUserResponse();
        assertNull(response.getUsers());
    }
}
