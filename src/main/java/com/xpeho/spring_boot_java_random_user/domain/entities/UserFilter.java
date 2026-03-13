package com.xpeho.spring_boot_java_random_user.domain.entities;

public record UserFilter(
        String gender,
        String firstname,
        String lastname,
        String civility,
        String email,
        String phone,
        String nat
) {
}
