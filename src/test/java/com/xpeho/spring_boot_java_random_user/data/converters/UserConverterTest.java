package com.xpeho.spring_boot_java_random_user.data.converters;

import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.data.models.db.User;
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
    void toDao_and_toDomain() {
        UserEntity entity = new UserEntity(1L, "male", "John", "Doe", "Mr", "john@doe.com", "1234", "pic.jpg", "FR");
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
