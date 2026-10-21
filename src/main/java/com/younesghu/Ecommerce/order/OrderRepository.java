package com.younesghu.Ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

        @Query("SELECT DISTINCT o FROM Order o " +
                        "LEFT JOIN FETCH o.user " +
                        "LEFT JOIN FETCH o.items i " +
                        "LEFT JOIN FETCH i.product " +
                        "WHERE o.id = :id")
        Optional<Order> findByIdWithItems(UUID id);

        @Query("SELECT DISTINCT o FROM Order o " +
                        "LEFT JOIN FETCH o.user " +
                        "LEFT JOIN FETCH o.items i " +
                        "LEFT JOIN FETCH i.product")
        List<Order> findAllWithUserAndItems();

        @Query("SELECT DISTINCT o FROM Order o " +
                        "LEFT JOIN FETCH o.user " +
                        "LEFT JOIN FETCH o.items i " +
                        "LEFT JOIN FETCH i.product " +
                        "WHERE o.user.id = :userId")
        List<Order> findAllByUserId(UUID userId);

        @Query("SELECT DISTINCT o FROM Order o " +
                        "LEFT JOIN FETCH o.user " +
                        "WHERE o.id = :orderId AND o.user.id = :userId")
        Optional<Order> findByIdWithUser(UUID orderId, UUID userId);
}
