package com.younesghu.Ecommerce.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInventoryRequest {
    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity must be 0 or more")
    private Integer availableQuantity;
}
