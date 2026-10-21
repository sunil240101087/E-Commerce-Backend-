package com.younesghu.Ecommerce.order.dto;

import com.younesghu.Ecommerce.order.item.dto.UpdateOrderItemRequest;
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
public class UpdateOrderRequest {
    @NotEmpty(message = "Order items are required")
    @Valid
    private List<UpdateOrderItemRequest> items;
}
