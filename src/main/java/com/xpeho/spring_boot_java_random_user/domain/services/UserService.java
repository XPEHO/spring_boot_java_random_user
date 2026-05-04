package com.xpeho.spring_boot_java_random_user.domain.services;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> saveAll(List<UserEntity> users);

    Optional<UserEntity> getById(long id);

    UserEntity save(UserEntity user);

    void deleteById(long id);

    PaginatedUsers filterUsers(UserFilter filter, int page, int size);
}


