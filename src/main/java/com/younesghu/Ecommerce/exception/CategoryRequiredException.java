package com.younesghu.Ecommerce.exception;

public class CategoryRequiredException extends RuntimeException {
    public CategoryRequiredException() {
        super("Category is required for a product");
    }
}
