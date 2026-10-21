package com.younesghu.Ecommerce.order;

import com.younesghu.Ecommerce.catalog.product.Product;
import com.younesghu.Ecommerce.common.BaseEntity;
import com.younesghu.Ecommerce.order.item.OrderItem;
import com.younesghu.Ecommerce.payment.Payment;
import com.younesghu.Ecommerce.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalAmount;

    public Order(User user, BigDecimal totalAmount) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount cannot be null or negative");
        }
        this.user = user;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.CREATED;
    }

    public OrderItem addItem(Product product, int quantity, BigDecimal priceAtPurchase) {
        OrderItem item = new OrderItem(this, product, quantity, priceAtPurchase);
        this.items.add(item);
        adjustTotalAmount(priceAtPurchase.multiply(BigDecimal.valueOf(quantity)));
        return item;
    }

    public void removeItem(OrderItem item) {
        if (item == null) {
            return;
        }
        if (this.items.remove(item)) {
            adjustTotalAmount(item.getPriceAtPurchase()
                    .multiply(BigDecimal.valueOf(item.getQuantity()))
                    .negate());
        }
    }

    private void adjustTotalAmount(BigDecimal delta) {
        if (delta == null) {
            return;
        }
        BigDecimal updatedTotal = this.totalAmount.add(delta);
        if (updatedTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        this.totalAmount = updatedTotal;
    }

    public boolean isEditable() {
        return this.status == OrderStatus.CREATED || this.status == OrderStatus.PROCESSING;
    }

    public void markProcessing() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Order must be CREATED");
        }
        this.status = OrderStatus.PROCESSING;
    }

    public void handleSuccessfulPayment() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Order must be CREATED");
        }
        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        if (status == OrderStatus.PAID || status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel paid/shipped order");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void recalculateTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : this.items) {
            if (item.getPriceAtPurchase() == null || item.getQuantity() == null) {
                continue;
            }
            total = total.add(item.getPriceAtPurchase()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        this.totalAmount = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
