package com.xpeho.spring_boot_java_random_user.domain.entities;

import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;

public record UserFilter(
        Gender gender,
        String firstname,
        String lastname,
        String civility,
        String email,
        String phone,
        String nat
) {
}
