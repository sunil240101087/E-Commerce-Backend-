package com.younesghu.Ecommerce.inventory.exception;

public class InvalidInventoryProductException extends RuntimeException {
    public InvalidInventoryProductException() {
        super("Product is required for inventory!");
    }
}
