package com.younesghu.Ecommerce.catalog.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Product title is required")
    @Size(min = 2, max = 100, message = "Product title must be between {min} and {max} characters")
    private String title;

    @Size(max = 500, message = "Product description must not exceed {max} characters")
    private String description;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;
}
