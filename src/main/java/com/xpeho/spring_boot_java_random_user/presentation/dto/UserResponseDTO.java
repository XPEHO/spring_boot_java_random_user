package com.xpeho.spring_boot_java_random_user.presentation.dto;

import java.util.List;

public record UserResponseDTO(List<UserDTO> data, int total, int skip, int limit) {
}
