package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.repositories.UserRepository;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> users) {
        Iterable<UserEntity> saved = userRepository.saveAll(users);
        return saved instanceof List<UserEntity> ? (List<UserEntity>) saved :
                java.util.stream.StreamSupport.stream(saved.spliterator(), false).toList();
    }
}
