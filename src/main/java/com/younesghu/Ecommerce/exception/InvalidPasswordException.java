package com.younesghu.Ecommerce.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password cannot be null or blank");
    }
}
