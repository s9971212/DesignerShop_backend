package com.designershop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    /**
     * 購物車ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private int cartId;

    /**
     * 創建該購物車的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * 上次更新該購物車的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * 使用者ID，表示這個購物車屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 購物車中的所有商品項目
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart other = (Cart) obj;
        return Objects.equals(cartId, other.cartId) && Objects.equals(userId, other.userId);
    }
}
