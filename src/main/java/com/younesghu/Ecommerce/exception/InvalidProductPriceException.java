package com.younesghu.Ecommerce.exception;

public class InvalidProductPriceException extends RuntimeException {
    public InvalidProductPriceException() {
        super("Product price cannot be null or negative");
    }
}
