package com.xpeho.spring_boot_java_random_user.data.models.api.randomuser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RandomUserPictureDTOTest {
    @Test
    @DisplayName("Should store and return medium picture URL")
    void shouldStoreAndReturnMediumPictureUrl() {
        RandomUserPictureDTO picture = new RandomUserPictureDTO();
        picture.setMedium("pic.jpg");

        assertEquals("pic.jpg", picture.getMedium());
    }

    @Test
    @DisplayName("Should have null medium by default")
    void shouldHaveNullMediumByDefault() {
        RandomUserPictureDTO picture = new RandomUserPictureDTO();

        assertNull(picture.getMedium());
    }
}

