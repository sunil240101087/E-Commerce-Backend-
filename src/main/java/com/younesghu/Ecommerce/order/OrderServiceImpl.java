package com.younesghu.Ecommerce.order;

import com.younesghu.Ecommerce.catalog.product.Product;
import com.younesghu.Ecommerce.catalog.product.ProductRepository;
import com.younesghu.Ecommerce.exception.OrderForbiddenException;
import com.younesghu.Ecommerce.exception.OrderNotEditableException;
import com.younesghu.Ecommerce.exception.ProductNotFoundException;
import com.younesghu.Ecommerce.exception.UserNotFoundException;
import com.younesghu.Ecommerce.order.dto.UpdateOrderRequest;
import com.younesghu.Ecommerce.order.item.OrderItem;
import jakarta.persistence.EntityNotFoundException;
import com.younesghu.Ecommerce.inventory.InventoryService;
import com.younesghu.Ecommerce.order.dto.CreateOrderRequest;
import com.younesghu.Ecommerce.order.item.dto.CreateOrderItemRequest;
import com.younesghu.Ecommerce.order.item.dto.UpdateOrderItemRequest;
import com.younesghu.Ecommerce.user.User;
import com.younesghu.Ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    @Override
    @Transactional(readOnly = true)
    public List<Order> listOrders() {
        return orderRepository.findAllWithUserAndItems();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> listUserOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order createOrder(UUID userId, CreateOrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order items are required");
        }

        Order order = new Order(user, BigDecimal.ZERO);
        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemRequest.getProductId()));

            Integer quantity = itemRequest.getQuantity();
            if (quantity == null) {
                throw new IllegalArgumentException("Quantity is required");
            }

            inventoryService.reserveProduct(product.getId(), quantity);
            order.addItem(product, quantity, product.getPrice());
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(UUID userId, UUID orderId, UpdateOrderRequest request) {

        Order order = loadAndValidateOrder(orderId, userId, request);

        // OrderItems requested from body to be changed
        Map<UUID, Integer> requestedItems = request.getItems().stream()
                .collect(Collectors.toMap(
                        UpdateOrderItemRequest::getProductId,
                        UpdateOrderItemRequest::getQuantity,
                        (a, b) -> {
                            throw new IllegalArgumentException("Duplicate product in order update");
                        }));

        Set<UUID> processedProductIds = new HashSet<>();
        List<OrderItem> existingItems = new ArrayList<>(order.getItems());

        // Existing Items that belong to Order that will be changed or removed
        for (OrderItem currentItem : existingItems) {
            UUID productId = currentItem.getProduct().getId();

            Integer newQuantity = requestedItems.get(productId);

            if (newQuantity == null || newQuantity == 0) {
                // Item removed from order
                inventoryService.releaseProduct(productId, currentItem.getQuantity());
                order.removeItem(currentItem);
            } else if (!newQuantity.equals(currentItem.getQuantity())) {
                // Quantity changed
                int difference = newQuantity - currentItem.getQuantity();

                if (difference > 0) {
                    // Need more inventory
                    inventoryService.reserveProduct(productId, difference);

                } else {
                    // Release excess inventory
                    inventoryService.releaseProduct(productId, -difference);
                }

                currentItem.updateQuantity(newQuantity);
                currentItem.updatePriceAtPurchase(currentItem.getProduct().getPrice()); // Update price if needed
            }
            // else: quantity unchanged, do nothing

            processedProductIds.add(productId);
        }

        // New Items that are not in the order that will be added
        for (UpdateOrderItemRequest itemRequest : request.getItems()) {
            UUID productId = itemRequest.getProductId();

            if (processedProductIds.contains(productId)) {
                continue; // Already processed
            }

            Integer quantity = itemRequest.getQuantity();
            if (quantity == null || quantity <= 0) {
                continue; // Skip invalid quantities
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            inventoryService.reserveProduct(productId, quantity);
            order.addItem(product, quantity, product.getPrice());
        }

        order.recalculateTotalAmount();

        return orderRepository.save(order);
    }

    private Order loadAndValidateOrder(UUID orderId, UUID userId, UpdateOrderRequest request) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderForbiddenException(orderId);
        }

        if (!order.isEditable()) {
            throw new OrderNotEditableException(orderId);
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order items are required");
        }

        return order;
    }

    @Override
    public Order cancelOrder(UUID userId, UUID orderId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderForbiddenException(orderId);
        }

        order.cancel();

        order.getItems().forEach(
                item -> inventoryService.releaseProduct(item.getProduct().getId(), item.getQuantity()));

        return order;
    }

    @Override
    public Order getOrderById(UUID userId, UUID orderId) {
        Order order = orderRepository.findByIdWithItems(orderId)
                .orElseThrow(EntityNotFoundException::new);

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderForbiddenException(orderId);
        }
        return order;
    }

}
