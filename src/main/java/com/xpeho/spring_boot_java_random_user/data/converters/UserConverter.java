package com.xpeho.spring_boot_java_random_user.data.converters;

import com.xpeho.spring_boot_java_random_user.data.models.api.dummy.DummyUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.models.api.randomuser.RandomUserResultDTO;
import com.xpeho.spring_boot_java_random_user.data.models.database.UserDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.springframework.stereotype.Service;


@Service
public class UserConverter {
    // Domain -> DAO
    public UserDAO toDao(UserEntity entity) {
        UserDAO userDAO = new UserDAO();
        userDAO.setId(entity.id());
        userDAO.setGender(entity.gender());
        userDAO.setFirstname(entity.firstname());
        userDAO.setLastname(entity.lastname());
        userDAO.setCivility(entity.civility());
        userDAO.setEmail(entity.email());
        userDAO.setPhone(entity.phone());
        userDAO.setPicture(entity.picture());
        userDAO.setNationality(entity.nat());
        return userDAO;
    }

    // DAO -> Domain
    public UserEntity toDomain(UserDAO userDAO) {
        return new UserEntity(
                userDAO.getId(),
                userDAO.getGender(),
                userDAO.getFirstname(),
                userDAO.getLastname(),
                userDAO.getCivility(),
                userDAO.getEmail(),
                userDAO.getPhone(),
                userDAO.getPicture(),
                userDAO.getNationality()
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
