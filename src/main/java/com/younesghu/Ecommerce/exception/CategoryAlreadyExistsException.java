package com.younesghu.Ecommerce.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String title) {
        super("Category already exists with title: " + title);
    }
}
