package com.younesghu.Ecommerce.payment.stripe;

import com.younesghu.Ecommerce.order.Order;
import com.younesghu.Ecommerce.order.OrderRepository;
import com.younesghu.Ecommerce.payment.PaymentRepository;
import com.younesghu.Ecommerce.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void handleCheckoutCompleted(String sessionId, String orderIdMetadata) {
        paymentRepository.findByProviderPaymentId(sessionId)
                .ifPresentOrElse(payment -> {

                    if (payment.getStatus() == PaymentStatus.SUCCEEDED) {
                        log.info("Payment {} already processed (SUCCEEDED), skipping.", payment.getId());
                        return;
                    }

                    payment.markSucceeded();
                    Order order = payment.getOrder();
                    order.handleSuccessfulPayment();

                    orderRepository.save(order);
                    paymentRepository.save(payment);

                    log.info("Marked payment {} as SUCCEEDED and order {} as PAID",
                            payment.getId(), order.getId());
                }, () -> log.warn("No payment found for Stripe session id {}", sessionId));
    }

    @Override
    @Transactional
    public void handleCheckoutFailed(String sessionId, String eventType) {
        paymentRepository.findByProviderPaymentId(sessionId)
                .ifPresentOrElse(payment -> {
                    payment.markFailed();
                    paymentRepository.save(payment);
                    log.info("Marked payment {} as FAILED for session {}", payment.getId(), sessionId);
                }, () -> log.warn("No payment found for Stripe session id {} on {}", sessionId, eventType));
    }
}
