package com.younesghu.Ecommerce.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    @Query("SELECT i from Inventory i LEFT JOIN FETCH i.product")
    List<Inventory> findAllwithProducts();

    @Query("SELECT i from Inventory i LEFT JOIN FETCH i.product WHERE i.product.id = :productId")
    Optional<Inventory> findInventoryByProductId(UUID productId);

}
