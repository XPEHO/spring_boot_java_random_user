package com.xpeho.spring_boot_java_random_user.presentation.dto;

public record UserRequest(
        String gender,
        String firstname,
        String lastname,
        String civility,
        String email,
        String phone,
        String picture,
        String nat
) {
}

