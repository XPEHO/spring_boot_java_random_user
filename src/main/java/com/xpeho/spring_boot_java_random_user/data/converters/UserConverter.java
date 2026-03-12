package com.xpeho.spring_boot_java_random_user.data.converters;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.springframework.stereotype.Service;


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

    // API DTO -> Domain
    public UserEntity fromApiModel(DummyUserResultDTO model) {
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

    // RandomUser API DTO -> Domain
    public UserEntity fromRandomUserApiModel(RandomUserResultDTO model) {
        String title = model.getName() != null ? model.getName().getTitle() : null;
        String first = model.getName() != null ? model.getName().getFirst() : null;
        String last = model.getName() != null ? model.getName().getLast() : null;
        String picture = model.getPicture() != null ? model.getPicture().getMedium() : null;

        return new UserEntity(
                null,
                model.getGender(),
                first,
                last,
                title,
                model.getEmail(),
                model.getPhone(),
                picture,
                model.getNat()
        );
    }
}
