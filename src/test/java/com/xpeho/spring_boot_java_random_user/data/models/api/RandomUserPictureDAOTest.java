package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserPictureDAOTest {
    @Test
    @DisplayName("Should store and return medium picture URL")
    void shouldStoreAndReturnMediumPictureUrl() {
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        picture.setMedium("pic.jpg");
        assertEquals("pic.jpg", picture.getMedium());
    }
    @Test
    @DisplayName("Should have null medium by default")
    void shouldHaveNullMediumByDefault() {
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        assertNull(picture.getMedium());
    }
}
