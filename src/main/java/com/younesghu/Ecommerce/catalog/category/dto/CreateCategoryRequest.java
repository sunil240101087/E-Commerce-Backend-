package com.younesghu.Ecommerce.catalog.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = "Category title is required")
    @Size(min = 2, max = 50, message = "Category title must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category title can only contain letters, numbers, spaces, and hyphens")
    private String title;
    private String description;
}
