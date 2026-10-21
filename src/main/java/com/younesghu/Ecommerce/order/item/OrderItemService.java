package com.younesghu.Ecommerce.order.item;

import com.younesghu.Ecommerce.order.item.dto.UpdateOrderItemRequest;

import java.util.UUID;

public interface OrderItemService {

    OrderItem createOrderItem();
    void deleteOrderItemsByOrderId(UUID id);
    OrderItem updateOrderItem(UUID id, UpdateOrderItemRequest updateOrderItemRequest);
}
