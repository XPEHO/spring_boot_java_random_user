package com.xpeho.spring_boot_java_random_user.domain.services;

import com.xpeho.spring_boot_java_random_user.domain.entities.PaginatedUsers;

import java.io.IOException;

public interface RemoteUserService {
    PaginatedUsers fetchUsers(int page, int size) throws IOException;
}

