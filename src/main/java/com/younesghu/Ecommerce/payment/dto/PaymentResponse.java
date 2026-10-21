package com.younesghu.Ecommerce.payment.dto;

import com.younesghu.Ecommerce.payment.PaymentStatus;
import com.younesghu.Ecommerce.payment.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private UUID id;
    private UUID orderId;
    private PaymentStatus status;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal amount;
    private String currency;
    private Provider provider;
    private String providerPaymentId;
}
