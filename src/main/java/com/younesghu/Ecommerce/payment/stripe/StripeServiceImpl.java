package com.younesghu.Ecommerce.payment.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.younesghu.Ecommerce.order.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeServiceImpl implements StripeService {

        @Value("${stripe.secret-key}")
        private String stripeSecretKey;

        @Override
        public Session createCheckoutSession(Order order) throws StripeException {
            Stripe.apiKey = stripeSecretKey;

                SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://frontend-url.com/success")
                    .setCancelUrl("http://frontend-url.com/cancel")
                    .addLineItem(
                        SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                SessionCreateParams.LineItem.PriceData
                                    .builder()
                                    .setCurrency("usd")
                                    .setUnitAmount(order
                                    .getTotalAmount()
                                    .multiply(BigDecimal.valueOf(100)).longValue())
                                    .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order #" + order.getId())
                                        .build()
                                    )
                                .build()
                            )
                            .build())
                        .putMetadata("orderId", order.getId().toString())
                    .build();
            return Session.create(params);
        }
}
