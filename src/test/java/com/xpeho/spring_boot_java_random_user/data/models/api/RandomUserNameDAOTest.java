package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserNameDAOTest {
    @Test
    @DisplayName("Should store and return all name fields")
    void shouldStoreAndReturnAllNameFields() {
        RandomUserNameDAO name = new RandomUserNameDAO();
        name.setFirst("John");
        name.setLast("Doe");
        name.setTitle("Mr");
        assertEquals("John", name.getFirst());
        assertEquals("Doe", name.getLast());
        assertEquals("Mr", name.getTitle());
    }
    @Test
    @DisplayName("Should have null fields by default")
    void shouldHaveNullFieldsByDefault() {
        RandomUserNameDAO name = new RandomUserNameDAO();
        assertNull(name.getFirst());
        assertNull(name.getLast());
        assertNull(name.getTitle());
    }
}
