package com.xpeho.spring_boot_java_random_user.data.converters;
import com.xpeho.spring_boot_java_random_user.data.models.api.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.data.models.db.User;
import org.springframework.stereotype.Service;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;


@Service
public class UserConverter {
    // Domain -> DAO
    public User toDao(UserEntity entity) {
        User user = new User();
        user.setId(entity.id());
        user.setGender(entity.gender());
        user.setFirstname(entity.firstname());
        user.setLastname(entity.lastname());
        user.setCivility(entity.civility());
        user.setEmail(entity.email());
        user.setPhone(entity.phone());
        user.setPicture(entity.picture());
        user.setNationality(entity.nat());
        return user;
    }
    // DAO -> Domain
    public UserEntity toDomain(User user) {
        return new UserEntity(
            user.getId(),
            user.getGender(),
            user.getFirstname(),
            user.getLastname(),
            user.getCivility(),
            user.getEmail(),
            user.getPhone(),
            user.getPicture(),
            user.getNationality()
        );
    }
    // API -> Domain
    public UserEntity fromApiModel(RandomUserResultDAO model) {
        return new UserEntity(
            null,
            model.getGender(),
            model.getFirstName(),
            model.getLastName(),
            null,
            model.getEmail(),
            model.getPhone(),
            model.getImage(),
            null
        );
    }

    // Domain -> DTO
    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(
            entity.id(),
            entity.gender(),
            entity.firstname(),
            entity.lastname(),
            entity.civility(),
            entity.email(),
            entity.phone(),
            entity.picture(),
            entity.nat()
        );
    }
}
