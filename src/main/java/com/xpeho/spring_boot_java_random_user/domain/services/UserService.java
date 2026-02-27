package com.xpeho.spring_boot_java_random_user.domain.services;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> saveAll(List<UserEntity> users);
}
