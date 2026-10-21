package com.younesghu.Ecommerce.order.dto;
import com.younesghu.Ecommerce.order.item.dto.CreateOrderItemRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotEmpty(message = "Order items are required")
    @Valid
    private List<CreateOrderItemRequest> items;
}
