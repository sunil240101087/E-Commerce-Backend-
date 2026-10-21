package com.younesghu.Ecommerce.exception;

import java.util.UUID;

public class CategoryHasProductsException extends RuntimeException {
    public CategoryHasProductsException(UUID categoryId) {
        super("Cannot delete category with id: " + categoryId + " because it has associated products");
    }
}
