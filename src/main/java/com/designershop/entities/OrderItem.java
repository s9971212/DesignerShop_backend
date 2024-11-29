package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    /**
     * 訂單項目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private int itemId;

    /**
     * 商品購買時的售價
     */
    @Column(name = "price_at_purchase", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    /**
     * 該商品的購買數量
     */
    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    /**
     * 商品ID，對應到某個商品
     */
    @Column(name = "product_id", nullable = false)
    private int productId;

    /**
     * 該訂單項目所屬的訂單
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public int hashCode() {
        return Objects.hash(itemId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        return Objects.equals(itemId, other.itemId) && Objects.equals(productId, other.productId);
    }
}
