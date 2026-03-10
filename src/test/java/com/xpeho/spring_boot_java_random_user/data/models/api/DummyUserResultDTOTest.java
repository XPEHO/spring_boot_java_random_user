package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DummyUserResultDTOTest {
    @Test
    @DisplayName("Should store and return all user fields")
    void shouldStoreAndReturnAllUserFields() {
        DummyUserResultDTO result = new DummyUserResultDTO();
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
    @DisplayName("Should have null fields by default")
    void shouldHaveNullFieldsByDefault() {
        DummyUserResultDTO result = new DummyUserResultDTO();
        assertNull(result.getGender());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getEmail());
        assertNull(result.getPhone());
        assertNull(result.getImage());
    }
}
