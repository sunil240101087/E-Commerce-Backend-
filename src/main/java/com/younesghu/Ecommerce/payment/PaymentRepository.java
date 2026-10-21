package com.younesghu.Ecommerce.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByProviderPaymentId(String providerPaymentId);

    List<Payment> findByOrderId(UUID orderId);

    List<Payment> findAllByOrderUserId(UUID userId);
}
