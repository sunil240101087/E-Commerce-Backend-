package com.younesghu.Ecommerce.catalog.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateCategoryRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 50, message = "Title must be between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Title can only contain letters, numbers, spaces, and hyphens")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Enabled is required")
    private Boolean enabled;

}
