package com.younesghu.Ecommerce.order.item;

import com.younesghu.Ecommerce.order.item.dto.UpdateOrderItemRequest;

import java.util.UUID;

public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public OrderItem createOrderItem() {
        throw new UnsupportedOperationException("Create order items via order creation");
    }

    @Override
    public OrderItem updateOrderItem(UUID id, UpdateOrderItemRequest request) {
        throw new UnsupportedOperationException("Update order items via order creation");
    }

    @Override
    public void deleteOrderItemsByOrderId(UUID id) {
        throw new UnsupportedOperationException("Delete order items via order creation");
    }

}
