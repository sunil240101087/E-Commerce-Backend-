package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.product.dto.CreateProductRequest;
import com.younesghu.Ecommerce.catalog.product.dto.ProductResponse;
import com.younesghu.Ecommerce.catalog.product.dto.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts() {
        List<ProductResponse> products = productService.listProducts()
                .stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> listProductsByCategory(@PathVariable UUID categoryId) {
        List<ProductResponse> productsByCategory = productService.getProductsByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseEntity.ok(productsByCategory);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest createProductRequest) {
        Product savedProduct = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(productMapper.toDto(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product updatedProduct = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
