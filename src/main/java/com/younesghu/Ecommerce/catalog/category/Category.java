package com.younesghu.Ecommerce.catalog.category;

import com.younesghu.Ecommerce.catalog.product.Product;
import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.exception.InvalidCategoryTitleException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category(String title) {
        changeTitle(title);
    }

    public Category(String title, String description) {
        changeTitle(title);
        changeDescription(description);
    }

    public void changeTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidCategoryTitleException();
        }
        this.title = title;
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

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Category))
            return false;
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
