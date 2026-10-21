package com.younesghu.Ecommerce.catalog.category;

import com.younesghu.Ecommerce.catalog.category.dto.CreateCategoryRequest;
import com.younesghu.Ecommerce.catalog.category.dto.UpdateCategoryRequest;
import com.younesghu.Ecommerce.catalog.product.ProductRepository;
import com.younesghu.Ecommerce.exception.CategoryAlreadyExistsException;
import com.younesghu.Ecommerce.exception.CategoryHasProductsException;
import com.younesghu.Ecommerce.exception.CategoryNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryRepository.findAllWithProducts();
    }

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new CategoryAlreadyExistsException(request.getTitle());
        }

        Category category = new Category(request.getTitle(), request.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, UpdateCategoryRequest request) {
        Category category = getCategoryById(id);

        category.changeTitle(request.getTitle());
        category.changeDescription(request.getDescription());

        if (Boolean.TRUE.equals(request.getEnabled())) {
            category.enable();
        } else if (Boolean.FALSE.equals(request.getEnabled())) {
            category.disable();
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = getCategoryById(id);

        if (!productRepository.findAllByCategory(id).isEmpty()) {
            throw new CategoryHasProductsException(id);
        }

        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
