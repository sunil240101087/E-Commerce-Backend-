package com.younesghu.Ecommerce.payment;

import com.younesghu.Ecommerce.payment.dto.CreatePaymentResponse;
import com.younesghu.Ecommerce.payment.dto.PaymentResponse;
import com.younesghu.Ecommerce.user.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> listPayments() {
        List<PaymentResponse> payments = paymentService.listAllPayments()
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();

        return ResponseEntity.ok(payments);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<CreatePaymentResponse>> listUserPayment(
            @AuthenticationPrincipal SecurityUser principal) {
        List<CreatePaymentResponse> payments = paymentService.listUserPayments(principal.getId())
                .stream()
                .map(paymentMapper::toDto)
                .toList();

        return ResponseEntity.ok(payments);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CreatePaymentResponse> getPayment(@PathVariable UUID id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(paymentMapper.toDto(payment));
    }
}
