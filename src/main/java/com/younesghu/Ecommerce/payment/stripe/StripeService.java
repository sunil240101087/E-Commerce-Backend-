package com.younesghu.Ecommerce.payment.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.younesghu.Ecommerce.order.Order;

public interface StripeService {

    Session createCheckoutSession(Order order) throws StripeException;
}
