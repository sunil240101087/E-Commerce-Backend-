package com.younesghu.Ecommerce.order.item;

import com.younesghu.Ecommerce.order.item.dto.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.title")
    OrderItemResponse toDto(OrderItem orderItem);
}
