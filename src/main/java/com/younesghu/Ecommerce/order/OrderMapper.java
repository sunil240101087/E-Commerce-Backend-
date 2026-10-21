package com.younesghu.Ecommerce.order;

import com.younesghu.Ecommerce.order.dto.OrderResponse;
import com.younesghu.Ecommerce.order.item.OrderItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderResponse toDto(Order order);
}
