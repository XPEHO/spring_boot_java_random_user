package com.xpeho.spring_boot_java_random_user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("User not found with id: " + id);
    }
}
