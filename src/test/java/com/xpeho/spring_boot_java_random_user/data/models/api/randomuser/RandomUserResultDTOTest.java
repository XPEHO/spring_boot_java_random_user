package com.xpeho.spring_boot_java_random_user.data.models.api.randomuser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RandomUserResultDTOTest {
    @Test
    @DisplayName("Should store and return all user fields")
    void shouldStoreAndReturnAllUserFields() {
        RandomUserNameDTO name = new RandomUserNameDTO();
        name.setTitle("Mrs");
        name.setFirst("Jane");
        name.setLast("Doe");

        RandomUserPictureDTO picture = new RandomUserPictureDTO();
        picture.setMedium("pic.jpg");

        RandomUserResultDTO result = new RandomUserResultDTO();
        result.setGender("female");
        result.setName(name);
        result.setEmail("jane@doe.com");
        result.setPhone("1234");
        result.setPicture(picture);
        result.setNat("FR");

        assertEquals("female", result.getGender());
        assertEquals(name, result.getName());
        assertEquals("jane@doe.com", result.getEmail());
        assertEquals("1234", result.getPhone());
        assertEquals(picture, result.getPicture());
        assertEquals("FR", result.getNat());
    }

    @Test
    @DisplayName("Should have null fields by default")
    void shouldHaveNullFieldsByDefault() {
        RandomUserResultDTO result = new RandomUserResultDTO();

        assertNull(result.getGender());
        assertNull(result.getName());
        assertNull(result.getEmail());
        assertNull(result.getPhone());
        assertNull(result.getPicture());
        assertNull(result.getNat());
    }
}

