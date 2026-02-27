package com.xpeho.spring_boot_java_random_user.data.converters;

import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserNameDAO;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserPictureDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.data.models.db.User;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserConverterTest {
    private final UserConverter converter = new UserConverter();

    @Test
    void fromApiModel_fullData() {
        RandomUserNameDAO name = new RandomUserNameDAO();
        name.first = "John";
        name.last = "Doe";
        name.title = "Mr";
        RandomUserPictureDAO picture = new RandomUserPictureDAO();
        picture.medium = "pic.jpg";
        RandomUserResultDAO api = new RandomUserResultDAO();
        api.gender = "male";
        api.name = name;
        api.email = "john@doe.com";
        api.phone = "1234";
        api.picture = picture;
        api.nat = "FR";
        UserEntity entity = converter.fromApiModel(api);
        assertNull(entity.id());
        assertEquals("male", entity.gender());
        assertEquals("John", entity.firstname());
        assertEquals("Doe", entity.lastname());
        assertEquals("Mr", entity.civility());
        assertEquals("john@doe.com", entity.email());
        assertEquals("1234", entity.phone());
        assertEquals("pic.jpg", entity.picture());
        assertEquals("FR", entity.nat());
    }

    @Test
    void fromApiModel_nullNameAndPicture() {
        RandomUserResultDAO api = new RandomUserResultDAO();
        api.gender = "female";
        api.name = null;
        api.email = "jane@doe.com";
        api.phone = "5678";
        api.picture = null;
        api.nat = "US";
        UserEntity entity = converter.fromApiModel(api);
        assertNull(entity.firstname());
        assertNull(entity.lastname());
        assertNull(entity.civility());
        assertNull(entity.picture());
        assertEquals("female", entity.gender());
        assertEquals("jane@doe.com", entity.email());
        assertEquals("5678", entity.phone());
        assertEquals("US", entity.nat());
    }

    @Test
    void toDto_and_toDao_and_toDomain() {
        UserEntity entity = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR");
        UserDTO dto = converter.toDto(entity);
        assertEquals(entity.id(), dto.id());
        assertEquals(entity.firstname(), dto.firstname());
        assertEquals(entity.lastname(), dto.lastname());
        assertEquals(entity.civility(), dto.civility());
        assertEquals(entity.email(), dto.email());
        assertEquals(entity.phone(), dto.phone());
        assertEquals(entity.picture(), dto.picture());
        assertEquals(entity.nat(), dto.nat());
        User dao = converter.toDao(entity);
        assertEquals(entity.id(), dao.getId());
        assertEquals(entity.firstname(), dao.getFirstName());
        assertEquals(entity.lastname(), dao.getLastName());
        assertEquals(entity.email(), dao.getEmail());
        assertEquals(entity.picture(), dao.getPictureUrl());
        UserEntity back = converter.toDomain(dao);
        assertEquals(entity.id(), back.id());
        assertEquals(entity.firstname(), back.firstname());
        assertEquals(entity.lastname(), back.lastname());
        assertEquals(entity.email(), back.email());
        assertEquals(entity.picture(), back.picture());
    }
}
