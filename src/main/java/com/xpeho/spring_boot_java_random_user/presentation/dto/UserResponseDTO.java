package com.xpeho.spring_boot_java_random_user.presentation.dto;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;

import java.util.List;

public record UserResponseDTO(List<UserEntity> data, int total, int skip, int limit) {
}
