package com.younesghu.Ecommerce.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Email cannot be null or blank");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
