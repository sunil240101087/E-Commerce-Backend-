package com.younesghu.Ecommerce.payment;

import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String providerPaymentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    public Payment(String providerPaymentId, Order order, PaymentStatus status, Provider provider, BigDecimal amount,
            String currency) {
        if (providerPaymentId == null) {
            throw new IllegalArgumentException("Provider payment ID cannot be null");
        }
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        this.providerPaymentId = providerPaymentId;
        this.order = order;
        this.status = status;
        this.provider = provider;
        this.amount = amount;
        this.currency = currency;
    }

    public void markSucceeded() {
        this.status = PaymentStatus.SUCCEEDED;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

}
