package com.xpeho.spring_boot_java_random_user.data.services;

import com.xpeho.spring_boot_java_random_user.data.converters.UserConverter;
import com.xpeho.spring_boot_java_random_user.data.models.database.UserDAO;
import com.xpeho.spring_boot_java_random_user.data.sources.api.RemoteUserService;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserSpecifications;
import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Map<UserSource, RemoteUserService> remoteUserServices;

    public UserServiceImpl(
            UserRepository userRepository,
            UserConverter userConverter,
            List<RemoteUserService> remoteUserServices
    ) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.remoteUserServices = remoteUserServices.stream()
                .collect(Collectors.toMap(RemoteUserService::getSource, Function.identity()));
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> users) {
        List<UserDAO> userDAOs = users.stream().map(userConverter::toDao).toList();
        Iterable<UserDAO> saved = userRepository.saveAll(userDAOs);
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
        UserDAO savedUserDAO = userRepository.save(userConverter.toDao(user));
        return userConverter.toDomain(savedUserDAO);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserEntity> filterUsers(UserFilter filter, Pageable pageable) {
        return userRepository.findAll(UserSpecifications.byFilter(filter), pageable)
                .map(userConverter::toDomain);
    }

    @Override
    public PaginatedUsers fetchAndSaveUsers(int page, int size, UserSource source) throws IOException {
        RemoteUserService remoteService = remoteUserServices.get(source);
        if (remoteService == null) {
            throw new IllegalStateException("No remote service configured for source: " + source);
        }
        PaginatedUsers response = remoteService.fetchUsers(page, size);
        saveAll(response.data());
        return response;
    }
}
