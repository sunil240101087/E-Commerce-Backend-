package com.younesghu.Ecommerce.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.younesghu.Ecommerce.exception.OrderForbiddenException;
import com.younesghu.Ecommerce.order.Order;
import com.younesghu.Ecommerce.order.OrderRepository;
import com.younesghu.Ecommerce.payment.dto.CreatePaymentResponse;
import com.younesghu.Ecommerce.payment.stripe.StripeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    @Override
    @Transactional(readOnly = true)
    public List<Payment> listAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> listUserPayments(UUID userId) {
        return paymentRepository.findAllByOrderUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
    }

    @Override
    public CreatePaymentResponse createPayment(UUID userId, UUID orderId) throws StripeException {
        Order order = orderRepository.findByIdWithUser(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderForbiddenException(orderId);
        }

        Session session = stripeService.createCheckoutSession(order);

        Payment payment = new Payment(
                session.getId(),
                order,
                PaymentStatus.PENDING,
                Provider.STRIPE,
                order.getTotalAmount(),
                "USD");

        Payment saved = paymentRepository.save(payment);

        return CreatePaymentResponse.builder()
                .paymentId(saved.getId())
                .providerPaymentId(saved.getProviderPaymentId())
                .checkoutUrl(session.getUrl())
                .build();
    }
}
