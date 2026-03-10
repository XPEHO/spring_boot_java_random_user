package com.xpeho.spring_boot_java_random_user.domain.entities;

import java.util.List;

public record PaginatedUsers(List<UserEntity> data, int total, int skip, int limit) {
}
