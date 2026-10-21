package com.younesghu.Ecommerce.exception;

public class InvalidProductTitleException extends RuntimeException {
    public InvalidProductTitleException() {
        super("Product title cannot be null or blank");
    }
}
