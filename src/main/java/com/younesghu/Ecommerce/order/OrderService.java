package com.younesghu.Ecommerce.order;

import com.younesghu.Ecommerce.order.dto.CreateOrderRequest;
import com.younesghu.Ecommerce.order.dto.UpdateOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> listOrders();

    List<Order> listUserOrders(UUID userId);

    Order createOrder(UUID userId, CreateOrderRequest request);

    Order updateOrder(UUID userId, UUID orderId, UpdateOrderRequest request);

    Order cancelOrder(UUID userId, UUID orderId);

    Order getOrderById(UUID userId, UUID orderId);
}
