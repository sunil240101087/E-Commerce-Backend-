package com.younesghu.Ecommerce.inventory.exception;

public class InsufficientReservedQuantityException extends RuntimeException {
    public InsufficientReservedQuantityException(int requested, int reserved) {
        super("Insufficient reserved quantity. Requested: " + requested + ", Reserved: " + reserved + ".");
    }
}
