package com.xpeho.spring_boot_java_random_user.domain.services;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;

import java.io.IOException;
import java.util.List;

public interface RandomUserProvider {
    List<UserEntity> fetchRandomUsers(int count) throws IOException;

}
