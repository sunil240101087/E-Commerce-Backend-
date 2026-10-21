package com.younesghu.Ecommerce.order.item.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemRequest {
    @NotNull(message = "product ID is required")
    private UUID productId;

    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "quantity should be more than 0")
    private Integer quantity;

}
