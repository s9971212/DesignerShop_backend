package com.designershop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    /**
     * 商品項目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private int itemId;

    /**
     * 該商品的購買數量
     */
    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    /**
     * 商品項目加入購物車的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;

    /**
     * 商品ID，對應到某個商品
     */
    @Column(name = "product_id", nullable = false)
    private int productId;

    /**
     * 該商品項目所屬的購物車
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

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
        CartItem other = (CartItem) obj;
        return Objects.equals(itemId, other.itemId) && Objects.equals(productId, other.productId);
    }
}
