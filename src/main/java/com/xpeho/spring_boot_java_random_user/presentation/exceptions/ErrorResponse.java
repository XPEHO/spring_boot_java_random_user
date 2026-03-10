package com.xpeho.spring_boot_java_random_user.presentation.exceptions;

public record ErrorResponse(String error, String message, int status) {
}


