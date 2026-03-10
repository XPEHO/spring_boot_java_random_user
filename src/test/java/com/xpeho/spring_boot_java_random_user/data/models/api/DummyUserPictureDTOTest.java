package com.xpeho.spring_boot_java_random_user.data.models.api;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserPictureDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DummyUserPictureDTOTest {
    @Test
    @DisplayName("Should store and return medium picture URL")
    void shouldStoreAndReturnMediumPictureUrl() {
        DummyUserPictureDTO picture = new DummyUserPictureDTO();
        picture.setMedium("pic.jpg");
        assertEquals("pic.jpg", picture.getMedium());
    }
    @Test
    @DisplayName("Should have null medium by default")
    void shouldHaveNullMediumByDefault() {
        DummyUserPictureDTO picture = new DummyUserPictureDTO();
        assertNull(picture.getMedium());
    }
}
