package com.younesghu.Ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse {

    private UUID paymentId;
    private String providerPaymentId;
    private String checkoutUrl;
}
