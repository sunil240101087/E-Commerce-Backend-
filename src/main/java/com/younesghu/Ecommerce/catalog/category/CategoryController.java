package com.younesghu.Ecommerce.catalog.category;

import com.younesghu.Ecommerce.catalog.category.dto.CategoryResponse;
import com.younesghu.Ecommerce.catalog.category.dto.CreateCategoryRequest;
import com.younesghu.Ecommerce.catalog.category.dto.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        List<CategoryResponse> categories = categoryService.listCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category savedCategory = categoryService.createCategory(createCategoryRequest);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id,
            @Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        Category updatedCategory = categoryService.updateCategory(id, updateCategoryRequest);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
