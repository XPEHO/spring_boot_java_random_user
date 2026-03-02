package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserResultDAOTest {
    @Test
    void testGetSetAllFields() {
        RandomUserResultDAO result = new RandomUserResultDAO();
        result.setGender("male");
        result.setFirstName("John");
        result.setLastName("Doe");
        result.setEmail("john@doe.com");
        result.setPhone("1234");
        result.setImage("pic.jpg");
        assertEquals("male", result.getGender());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john@doe.com", result.getEmail());
        assertEquals("1234", result.getPhone());
        assertEquals("pic.jpg", result.getImage());
    }

    @Test
    void testDefaultFieldsAreNull() {
        RandomUserResultDAO result = new RandomUserResultDAO();
        assertNull(result.getGender());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getEmail());
        assertNull(result.getPhone());
        assertNull(result.getImage());
    }
}
