package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserResponseTest {
    @Test
    void testGetSetUsers() {
        RandomUserResponse response = new RandomUserResponse();
        List<RandomUserResultDAO> users = List.of(new RandomUserResultDAO(), new RandomUserResultDAO());
        response.setUsers(users);
        assertEquals(users, response.getUsers());
    }

    @Test
    void testDefaultUsersIsNull() {
        RandomUserResponse response = new RandomUserResponse();
        assertNull(response.getUsers());
    }
}
