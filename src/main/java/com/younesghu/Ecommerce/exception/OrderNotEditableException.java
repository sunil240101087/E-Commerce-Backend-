package com.younesghu.Ecommerce.exception;

import java.util.UUID;

public class OrderNotEditableException extends RuntimeException {

    public OrderNotEditableException(UUID orderId) {
        super("Order " + orderId + " can only be edited when status is CREATED");
    }
}
