package com.younesghu.Ecommerce.exception;

import java.util.UUID;

public class DisabledCategoryException extends RuntimeException {
    public DisabledCategoryException(UUID categoryId) {
        super("Cannot assign product to disabled category with id: " + categoryId);
    }
}
