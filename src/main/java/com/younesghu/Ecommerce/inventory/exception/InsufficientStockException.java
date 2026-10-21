package com.younesghu.Ecommerce.inventory.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Insufficient amount from Stock!");
    }

    public InsufficientStockException(int requested, int available) {
        super("Insufficient stock. Requested: " + requested + ", Available: " + available + ".");
    }
}
