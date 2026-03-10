package com.xpeho.spring_boot_java_random_user.domain.exceptions;

public class InvalidPaginationException extends RuntimeException {
    public InvalidPaginationException(String message) {
        super(message);
    }

}

