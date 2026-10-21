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
public class UpdateOrderItemRequest {
    @NotNull(message = "product ID is required")
    private UUID productId;

    @NotNull(message = "quantity is required")
    @Min(value = 0, message = "quantity must be 0 or more")
    private Integer quantity;
}
