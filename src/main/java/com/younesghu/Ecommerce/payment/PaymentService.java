package com.younesghu.Ecommerce.payment;

import com.younesghu.Ecommerce.payment.dto.CreatePaymentResponse;

import com.stripe.exception.StripeException;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<Payment> listUserPayments(UUID userId);

    List<Payment> listAllPayments();

    Payment getPaymentById(UUID paymentId);

    CreatePaymentResponse createPayment(UUID userId, UUID orderId) throws StripeException;
}
