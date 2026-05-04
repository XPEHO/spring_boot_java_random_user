package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.database.UserDao;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserSpecifications;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        List<UserDao> daoUsers = users.stream().map(userConverter::toDao).toList();
        Iterable<UserDao> saved = userRepository.saveAll(daoUsers);
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
        UserDao savedUser = userRepository.save(userConverter.toDao(user));
        return userConverter.toDomain(savedUser);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public PaginatedUsers filterUsers(UserFilter filter, int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<UserDao> result = userRepository.findAll(UserSpecifications.byFilter(filter), pageable);
        List<UserEntity> entities = result.getContent().stream()
                .map(userConverter::toDomain)
                .toList();
        int skip = (page - 1) * size;
        return new PaginatedUsers(entities, (int) result.getTotalElements(), skip, size);
    }
}
