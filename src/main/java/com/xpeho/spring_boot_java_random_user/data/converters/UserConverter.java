package com.xpeho.spring_boot_java_random_user.data.converters;

import com.xpeho.spring_boot_java_random_user.data.models.RandomUserResultDAO;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {
    public UserEntity modelToEntity(RandomUserResultDAO model) {
        UserEntity entity = new UserEntity();
        entity.setGender(model.gender);
        entity.setFirstname(model.name.first);
        entity.setLastname(model.name.last);
        entity.setCivility(model.name.title);
        entity.setEmail(model.email);
        entity.setPhone(model.phone);
        entity.setPicture(model.picture.medium);
        entity.setNat(model.nat);
        return entity;
    }
}
