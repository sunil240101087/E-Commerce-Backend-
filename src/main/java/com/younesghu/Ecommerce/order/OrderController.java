package com.younesghu.Ecommerce.order;

import com.stripe.exception.StripeException;
import com.younesghu.Ecommerce.order.dto.CreateOrderRequest;
import com.younesghu.Ecommerce.order.dto.OrderResponse;
import com.younesghu.Ecommerce.order.dto.UpdateOrderRequest;
import com.younesghu.Ecommerce.payment.PaymentService;
import com.younesghu.Ecommerce.payment.dto.CreatePaymentResponse;
import com.younesghu.Ecommerce.user.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrder() {
        List<OrderResponse> orders = orderService.listOrders().stream().map(orderMapper::toDto)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<OrderResponse>> listUserOrder(
            @AuthenticationPrincipal SecurityUser principal) {
        List<OrderResponse> orders = orderService.listUserOrders(principal.getId()).stream().map(orderMapper::toDto)
                .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderResponse> getOrder(
            @AuthenticationPrincipal SecurityUser principal,
            @PathVariable UUID id) {
        Order order = orderService.getOrderById(principal.getId(), id);
        return ResponseEntity.ok(orderMapper.toDto(order));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal SecurityUser principal,
            @Valid @RequestBody CreateOrderRequest request) {
        Order createdOrder = orderService.createOrder(principal.getId(), request);
        return new ResponseEntity<>(orderMapper.toDto(createdOrder), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @AuthenticationPrincipal SecurityUser principal,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrderRequest request) {
        Order updatedOrder = orderService.updateOrder(principal.getId(), id, request);
        return ResponseEntity.ok(orderMapper.toDto(updatedOrder));
    }

    @PostMapping(path = "/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @AuthenticationPrincipal SecurityUser principal,
            @PathVariable UUID id) {
        Order cancelledOrder = orderService.cancelOrder(principal.getId(), id);
        return ResponseEntity.ok(orderMapper.toDto(cancelledOrder));
    }

    @PostMapping(path = "/{id}/pay")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @AuthenticationPrincipal SecurityUser principal,
            @PathVariable UUID id) throws StripeException {
        CreatePaymentResponse response = paymentService.createPayment(principal.getId(), id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
