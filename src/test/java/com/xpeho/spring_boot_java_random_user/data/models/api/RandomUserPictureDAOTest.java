package com.xpeho.spring_boot_java_random_user.data.models.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomUserPictureDAOTest {
    @Test
    void testGetSetMedium() {
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        picture.setMedium("pic.jpg");
        assertEquals("pic.jpg", picture.getMedium());
    }
    @Test
    void testDefaultMediumIsNull() {
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        assertNull(picture.getMedium());
    }
}
