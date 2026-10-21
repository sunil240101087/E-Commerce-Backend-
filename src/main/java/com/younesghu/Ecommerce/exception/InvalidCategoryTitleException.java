package com.younesghu.Ecommerce.exception;

public class InvalidCategoryTitleException extends RuntimeException {
    public InvalidCategoryTitleException() {
        super("Category title cannot be null or blank");
    }
}
