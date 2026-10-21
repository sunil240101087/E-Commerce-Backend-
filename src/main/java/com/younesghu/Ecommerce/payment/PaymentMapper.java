package com.younesghu.Ecommerce.payment;

import com.younesghu.Ecommerce.payment.dto.CreatePaymentResponse;
import com.younesghu.Ecommerce.payment.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    @Mapping(target = "paymentId", source = "id")
    CreatePaymentResponse toDto(Payment payment);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "firstName", source = "order.user.firstName")
    @Mapping(target = "lastName", source = "order.user.lastName")
    @Mapping(target = "email", source = "order.user.email")
    PaymentResponse toPaymentResponse(Payment payment);
}
