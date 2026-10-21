package com.younesghu.Ecommerce.inventory;

import com.younesghu.Ecommerce.catalog.product.Product;
import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.inventory.exception.InsufficientReservedQuantityException;
import com.younesghu.Ecommerce.inventory.exception.InsufficientStockException;
import com.younesghu.Ecommerce.inventory.exception.InvalidInventoryProductException;
import com.younesghu.Ecommerce.inventory.exception.InvalidInventoryQuantityException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "inventory")
@Getter
@NoArgsConstructor
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "reserved_quantity")
    private int reservedQuantity;

    public Inventory(Product product, int initialQuantity) {
        if (product == null) {
            throw new InvalidInventoryProductException();
        }
        if (initialQuantity < 0) {
            throw new InvalidInventoryQuantityException("Initial quantity must be 0 or more!");
        }
        this.product = product;
        this.availableQuantity = initialQuantity;
        this.reservedQuantity = 0;
    }

    public void reserve(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInventoryQuantityException("Reserve quantity must be more than 0!");
        }
        if (availableQuantity < quantity) {
            throw new InsufficientStockException(quantity, availableQuantity);
        }

        availableQuantity -= quantity;
        reservedQuantity += quantity;
    }

    public void release(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInventoryQuantityException("Release quantity must be more than 0!");
        }
        if (reservedQuantity < quantity) {
            throw new InsufficientReservedQuantityException(quantity, reservedQuantity);
        }
        reservedQuantity -= quantity;
        availableQuantity += quantity;
    }

    public void confirm(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInventoryQuantityException("Confirm quantity must be more than 0!");
        }
        if (reservedQuantity < quantity) {
            throw new InsufficientReservedQuantityException(quantity, reservedQuantity);
        }
        reservedQuantity -= quantity;
    }

    public void changeAvailableQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidInventoryQuantityException("Available quantity must be 0 or more!");
        }
        this.availableQuantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Inventory))
            return false;
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
