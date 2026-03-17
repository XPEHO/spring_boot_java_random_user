package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserSpecifications;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.services.LocalUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service

public class UserServiceImpl implements LocalUserService {
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

    @Override
    public Optional<UserEntity> getById(long id) {
        return userRepository.findById(id)
                .map(userConverter::toDomain);
    }

    @Override
    public UserEntity save(UserEntity user) {
        User savedUser = userRepository.save(userConverter.toDao(user));
        return userConverter.toDomain(savedUser);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> filterUsers(UserFilter filter) {
        return userRepository.findAll(UserSpecifications.byFilter(filter)).stream()
                .map(userConverter::toDomain)
                .toList();
    }
}
