package com.younesghu.Ecommerce.catalog.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
    List<Product> findAllWithCategory();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findAllByCategory(UUID categoryId);
}
