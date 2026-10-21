package com.younesghu.Ecommerce.exception;

import java.util.UUID;

public class OrderForbiddenException extends RuntimeException {

    public OrderForbiddenException(UUID orderId) {
        super("You are not allowed to modify order " + orderId);
    }
}
