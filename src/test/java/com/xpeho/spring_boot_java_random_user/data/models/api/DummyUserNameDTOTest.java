package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserNameDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DummyUserNameDTOTest {
    @Test
    @DisplayName("Should store and return all name fields")
    void shouldStoreAndReturnAllNameFields() {
        DummyUserNameDTO name = new DummyUserNameDTO();
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
        DummyUserNameDTO name = new DummyUserNameDTO();
        assertNull(name.getFirst());
        assertNull(name.getLast());
        assertNull(name.getTitle());
    }
}
