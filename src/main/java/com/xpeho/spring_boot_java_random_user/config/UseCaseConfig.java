package com.xpeho.spring_boot_java_random_user.config;

import com.xpeho.spring_boot_java_random_user.domain.services.RemoteUserService;
import com.xpeho.spring_boot_java_random_user.domain.services.UserService;
import com.xpeho.spring_boot_java_random_user.domain.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserService userService) {
        return new CreateUserUseCase(userService);
    }

    @Bean
    public DeleteUserByIdUseCase deleteUserByIdUseCase(UserService userService) {
        return new DeleteUserByIdUseCase(userService);
    }

    @Bean
    public FetchAndSaveRandomUsersUseCase fetchAndSaveRandomUsersUseCase(
            UserService userService,
            List<RemoteUserService> remoteUserServices) {
        return new FetchAndSaveRandomUsersUseCase(userService, remoteUserServices);
    }

    @Bean
    public FilterUsersUseCase filterUsersUseCase(UserService userService) {
        return new FilterUsersUseCase(userService);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(UserService userService) {
        return new GetUserByIdUseCase(userService);
    }

    @Bean
    public UpdateRandomUserUseCase updateRandomUserUseCase(UserService userService) {
        return new UpdateRandomUserUseCase(userService);
    }
}
