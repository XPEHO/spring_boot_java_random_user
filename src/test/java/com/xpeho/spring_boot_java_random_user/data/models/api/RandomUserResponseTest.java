package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserResponseTest {
    @Test
    void testGetSetResults() {
        RandomUserResponse response = new RandomUserResponse();
        RandomUserResultDAO[] arr = new RandomUserResultDAO[2];
        arr[0] = new RandomUserResultDAO();
        arr[1] = new RandomUserResultDAO();
        response.setResults(arr);
        assertArrayEquals(arr, response.getResults());
    }

    @Test
    void testDefaultResultsIsNull() {
        RandomUserResponse response = new RandomUserResponse();
        assertNull(response.getResults());
    }
}
