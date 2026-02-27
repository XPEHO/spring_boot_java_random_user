package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserNameDAOTest {
    @Test
    void testGetSetAllFields() {
        RandomUserNameDAO name = new RandomUserNameDAO();
        name.setFirst("John");
        name.setLast("Doe");
        name.setTitle("Mr");
        assertEquals("John", name.getFirst());
        assertEquals("Doe", name.getLast());
        assertEquals("Mr", name.getTitle());
    }
    @Test
    void testDefaultFieldsAreNull() {
        RandomUserNameDAO name = new RandomUserNameDAO();
        assertNull(name.getFirst());
        assertNull(name.getLast());
        assertNull(name.getTitle());
    }
}
