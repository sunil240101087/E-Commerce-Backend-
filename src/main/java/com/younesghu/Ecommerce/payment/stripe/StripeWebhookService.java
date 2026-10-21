package com.younesghu.Ecommerce.payment.stripe;

public interface StripeWebhookService {

    void handleCheckoutCompleted(String sessionId, String orderIdMetadata);

    void handleCheckoutFailed(String sessionId, String eventType);
}

