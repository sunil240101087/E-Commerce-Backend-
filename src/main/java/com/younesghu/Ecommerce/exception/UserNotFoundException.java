package com.younesghu.Ecommerce.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }

    public UserNotFoundException(UUID userId) {
        super("User not found with id: " + userId);
    }
}
