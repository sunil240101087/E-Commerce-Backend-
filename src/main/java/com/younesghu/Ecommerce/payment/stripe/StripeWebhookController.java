package com.younesghu.Ecommerce.payment.stripe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @PostMapping("/stripe")
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            log.warn("Stripe webhook signature verification failed", e);
            return ResponseEntity.badRequest().build();
        }

        String type = event.getType();

        if ("checkout.session.completed".equals(type)) {
            String sessionId = null;
            String orderIdMetadata = null;

            try {
                JsonNode root = objectMapper.readTree(payload);
                JsonNode dataObject = root.path("data").path("object");
                sessionId = dataObject.path("id").asText(null);
                orderIdMetadata = dataObject.path("metadata").path("orderId").asText(null);
            } catch (Exception e) {
                log.warn("Failed to parse Stripe webhook payload JSON", e);
            }

            if (sessionId == null || sessionId.isEmpty()) {
                log.warn("Stripe webhook checkout.session.completed without session id");
                return ResponseEntity.ok().build();
            }

            log.info("Handling checkout.session.completed for session {} and orderId metadata {}",
                    sessionId, orderIdMetadata);

            stripeWebhookService.handleCheckoutCompleted(sessionId, orderIdMetadata);

        } else if ("checkout.session.expired".equals(type)
                || "checkout.session.async_payment_failed".equals(type)) {
            // Same payload shape: data.object.id is the Checkout Session id
            String sessionId = null;

            try {
                JsonNode root = objectMapper.readTree(payload);
                JsonNode dataObject = root.path("data").path("object");
                sessionId = dataObject.path("id").asText(null);
            } catch (Exception e) {
                log.warn("Failed to parse Stripe webhook payload JSON for failure event", e);
            }

            if (sessionId == null || sessionId.isEmpty()) {
                log.warn("Stripe webhook {} without session id", type);
                return ResponseEntity.ok().build();
            }

            stripeWebhookService.handleCheckoutFailed(sessionId, type);
        }

        return ResponseEntity.ok().build();
    }
}