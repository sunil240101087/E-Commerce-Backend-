package com.younesghu.Ecommerce.order.item;

import com.younesghu.Ecommerce.catalog.product.Product;
import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal priceAtPurchase;

    public OrderItem(Order order, Product product, int quantity, BigDecimal priceAtPurchase) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (priceAtPurchase == null || priceAtPurchase.signum() < 0) {
            throw new IllegalArgumentException("Price at purchase cannot be null or negative");
        }
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }

    public void updatePriceAtPurchase(BigDecimal newPriceAtPurchase) {
        if (newPriceAtPurchase == null || newPriceAtPurchase.signum() < 0) {
            throw new IllegalArgumentException("Price at purchase cannot be null or negative");
        }
        this.priceAtPurchase = newPriceAtPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OrderItem))
            return false;
        return id != null && id.equals(((OrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
