package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.category.Category;
import com.younesghu.Ecommerce.catalog.category.CategoryRepository;
import com.younesghu.Ecommerce.catalog.product.dto.CreateProductRequest;
import com.younesghu.Ecommerce.exception.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductServerImpl Unit Tests")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category testCategory;

    @Nested
    @DisplayName("Create Product Tests")
    class CreateProductTests {

        @Test
        @DisplayName("Should create product with valid request and existing category")
        void shouldCreateProductSuccessfully() {
            // given
            UUID categoryId = UUID.randomUUID();

            CreateProductRequest request = new CreateProductRequest();
            request.setTitle("Test product");
            request.setPrice(BigDecimal.valueOf(19.99));
            request.setCategoryId(categoryId);

            Category category = new Category("Test category");
            when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

            when(productRepository.save(any(Product.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Product result = productService.createProduct(request);

            // then
            assertNotNull(result);
            assertEquals("Test product", result.getTitle());
            assertEquals(BigDecimal.valueOf(19.99), result.getPrice());
            assertEquals(category, result.getCategory());

            verify(categoryRepository).findById(categoryId);
            verify(productRepository).save(any(Product.class));
        }

        @Test
        @DisplayName("Should throw when category does not exist")
        void shouldThrowWhenCategoryDoesNotExist() {
            // given
            UUID categoryId = UUID.randomUUID();

            CreateProductRequest request = new CreateProductRequest();
            request.setTitle("Test product");
            request.setPrice(BigDecimal.valueOf(19.99));
            request.setCategoryId(categoryId);

            // category repo returns empty -> not found
            when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

            // when / then
            assertThrows(CategoryNotFoundException.class,
                    () -> productService.createProduct(request));

            verify(categoryRepository).findById(categoryId);
            verify(productRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should create product disabled when enabled is false")
        void shouldCreateProductDisabledWhenEnabledFalse() {
            // given
            UUID categoryId = UUID.randomUUID();

            CreateProductRequest request = new CreateProductRequest();
            request.setTitle("Disabled product");
            request.setPrice(BigDecimal.valueOf(9.99));
            request.setCategoryId(categoryId);
            request.setEnabled(false);

            Category category = new Category("Test category");
            when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
            when(productRepository.save(any(Product.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Product result = productService.createProduct(request);

            // then
            assertNotNull(result);
            assertFalse(result.isEnabled());
            verify(productRepository).save(any(Product.class));
        }

        @Test
        @DisplayName("Should create product enabled when enabled is null or true")
        void shouldCreateProductEnabledWhenEnabledNullOrTrue() {
            // given - enabled not set (null) => default enabled
            UUID categoryId = UUID.randomUUID();

            CreateProductRequest request = new CreateProductRequest();
            request.setTitle("Enabled product");
            request.setPrice(BigDecimal.valueOf(9.99));
            request.setCategoryId(categoryId);
            // request.setEnabled(null);  // default

            Category category = new Category("Test category");
            when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
            when(productRepository.save(any(Product.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Product result = productService.createProduct(request);

            // then
            assertNotNull(result);
            assertTrue(result.isEnabled());
            verify(productRepository).save(any(Product.class));
        }
    }

}