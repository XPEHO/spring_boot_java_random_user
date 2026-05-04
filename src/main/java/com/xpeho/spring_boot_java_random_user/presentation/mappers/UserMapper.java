package com.xpeho.spring_boot_java_random_user.presentation.mappers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserDTO;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDTO toDTO(UserEntity entity) {
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

