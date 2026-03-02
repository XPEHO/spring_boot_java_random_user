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
        RandomUserResultDAO api = new RandomUserResultDAO();
        api.setGender("male");
        api.setFirstName("John");
        api.setLastName("Doe");
        api.setEmail("john@doe.com");
        api.setPhone("1234");
        api.setImage("pic.jpg");
        UserEntity entity = converter.fromApiModel(api);
        assertNull(entity.id());
        assertEquals("male", entity.gender());
        assertEquals("John", entity.firstname());
        assertEquals("Doe", entity.lastname());
        assertEquals("john@doe.com", entity.email());
        assertEquals("1234", entity.phone());
        assertEquals("pic.jpg", entity.picture());
    }

    @Test
    void fromApiModel_nullFields() {
        RandomUserResultDAO api = new RandomUserResultDAO();
        api.setGender("female");
        api.setEmail("jane@doe.com");
        api.setPhone("5678");
        UserEntity entity = converter.fromApiModel(api);
        assertNull(entity.firstname());
        assertNull(entity.lastname());
        assertNull(entity.picture());
        assertEquals("female", entity.gender());
        assertEquals("jane@doe.com", entity.email());
        assertEquals("5678", entity.phone());
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
        assertEquals(entity.firstname(), dao.getFirstname());
        assertEquals(entity.lastname(), dao.getLastname());
        assertEquals(entity.email(), dao.getEmail());
        assertEquals(entity.picture(), dao.getPicture());
        UserEntity back = converter.toDomain(dao);
        assertEquals(entity.id(), back.id());
        assertEquals(entity.firstname(), back.firstname());
        assertEquals(entity.lastname(), back.lastname());
        assertEquals(entity.email(), back.email());
        assertEquals(entity.picture(), back.picture());
    }
}
