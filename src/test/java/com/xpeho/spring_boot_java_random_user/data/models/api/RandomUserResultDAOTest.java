package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserResultDAOTest {
    @Test
    void testGetSetAllFields() {
        RandomUserResultDAO result = new RandomUserResultDAO();
        result.setGender("male");
        RandomUserNameDAO name = new RandomUserNameDAO();
        result.setName(name);
        result.setEmail("john@doe.com");
        result.setPhone("1234");
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        result.setPicture(picture);
        result.setNat("FR");
        assertEquals("male", result.getGender());
        assertSame(name, result.getName());
        assertEquals("john@doe.com", result.getEmail());
        assertEquals("1234", result.getPhone());
        assertSame(picture, result.getPicture());
        assertEquals("FR", result.getNat());
    }
    @Test
    void testDefaultFieldsAreNull() {
        RandomUserResultDAO result = new RandomUserResultDAO();
        assertNull(result.getGender());
        assertNull(result.getName());
        assertNull(result.getEmail());
        assertNull(result.getPhone());
        assertNull(result.getPicture());
        assertNull(result.getNat());
    }
}
