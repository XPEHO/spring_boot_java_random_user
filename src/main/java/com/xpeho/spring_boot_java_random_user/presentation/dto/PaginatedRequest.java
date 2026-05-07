package com.xpeho.spring_boot_java_random_user.presentation.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record PaginatedRequest(int page, int size) {
    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}

