package com.younesghu.Ecommerce.inventory.exception;

public class InvalidInventoryQuantityException extends RuntimeException {
    public InvalidInventoryQuantityException() {
        super("Invalid Quantity, should be more than 0!");
    }

    public InvalidInventoryQuantityException(String message) {
        super(message);
    }
}
