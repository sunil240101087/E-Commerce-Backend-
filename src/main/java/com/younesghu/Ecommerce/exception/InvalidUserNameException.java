package com.younesghu.Ecommerce.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException() {
        super("First name and last name cannot be null or blank");
    }
}
