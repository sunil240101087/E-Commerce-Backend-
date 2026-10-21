package com.younesghu.Ecommerce.catalog.product;

import com.younesghu.Ecommerce.catalog.category.Category;
import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.exception.CategoryRequiredException;
import com.younesghu.Ecommerce.exception.DisabledCategoryException;
import com.younesghu.Ecommerce.exception.InvalidProductPriceException;
import com.younesghu.Ecommerce.exception.InvalidProductTitleException;
import com.younesghu.Ecommerce.inventory.Inventory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inventory inventory;

    @Column(nullable = false)
    private boolean enabled = true;

    public Product(String title, BigDecimal price, Category category) {
        changeTitle(title);
        changePrice(price);
        changeCategory(category);
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidProductTitleException();
        }
        this.title = title;
    }

    public void changePrice(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new InvalidProductPriceException();
        }
        this.price = price;
    }

    public void changeCategory(Category category) {
        if (category == null) {
            throw new CategoryRequiredException();
        }
        if (!category.isEnabled()) {
            throw new DisabledCategoryException(category.getId());
        }
        this.category = category;
    }

    public void attachInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void disable() {
        this.enabled = false;
    }

    public void enable() {
        this.enabled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
