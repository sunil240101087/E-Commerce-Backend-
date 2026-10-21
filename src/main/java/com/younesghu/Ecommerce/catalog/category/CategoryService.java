package com.younesghu.Ecommerce.catalog.category;

import com.younesghu.Ecommerce.catalog.category.dto.CreateCategoryRequest;
import com.younesghu.Ecommerce.catalog.category.dto.UpdateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(CreateCategoryRequest createCategoryRequest);
    void deleteCategory(UUID id);
    Category getCategoryById(UUID id);
    Category updateCategory(UUID id, UpdateCategoryRequest updateCategoryRequest);
}
