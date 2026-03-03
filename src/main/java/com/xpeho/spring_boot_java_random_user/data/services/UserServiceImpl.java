package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import com.xpeho.spring_boot_java_random_user.data.models.db.User;
import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> users) {
        List<User> daoUsers = users.stream().map(userConverter::toDao).toList();
        Iterable<User> saved = userRepository.saveAll(daoUsers);
        return StreamSupport.stream(saved.spliterator(), false)
            .map(userConverter::toDomain)
            .toList();
    }
}
