package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.category.Category;
import com.younesghu.Ecommerce.catalog.category.CategoryRepository;
import com.younesghu.Ecommerce.catalog.product.dto.CreateProductRequest;
import com.younesghu.Ecommerce.catalog.product.dto.UpdateProductRequest;
import com.younesghu.Ecommerce.exception.CategoryNotFoundException;
import com.younesghu.Ecommerce.exception.ProductNotFoundException;
import com.younesghu.Ecommerce.inventory.Inventory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> listProducts() {
        return productRepository.findAllWithCategory();
    }

    @Override
    public List<Product> getProductsByCategoryId(UUID categoryId) {
        return productRepository.findAllByCategory(categoryId);
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        // Validate category exists
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        // Create product using constructor (validates title, price, and category)
        Product product = new Product(
                request.getTitle(),
                request.getPrice(),
                category);

        // Set optional description field
        if (request.getDescription() != null) {
            product.changeDescription(request.getDescription());
        }

        // Set enabled field if explicitly disabled
        if (request.getEnabled() != null && !request.getEnabled()) {
            product.disable();
        }

        Inventory inventory = new Inventory(product, 0);
        product.attachInventory(inventory);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID productId, UpdateProductRequest request) {

        Product product = getProductById(productId);

        if (request.getTitle() != null) {
            product.changeTitle(request.getTitle());
        }

        if (request.getPrice() != null) {
            product.changePrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            product.changeDescription(request.getDescription());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

            product.changeCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
