package com.younesghu.Ecommerce.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private UUID id;
    private UUID productId;
    private String productTitle;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
