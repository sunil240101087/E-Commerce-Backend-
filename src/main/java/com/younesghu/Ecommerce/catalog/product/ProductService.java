package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.product.dto.CreateProductRequest;
import com.younesghu.Ecommerce.catalog.product.dto.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> listProducts();
    List<Product> getProductsByCategoryId(UUID categoryId);
    Product getProductById(UUID id);
    Product createProduct(CreateProductRequest createProductRequest);
    Product updateProduct(UUID id, UpdateProductRequest updateProductRequest);
    void deleteProduct(UUID id);
}
