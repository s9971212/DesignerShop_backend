package com.designershop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductLikesId implements Serializable {

    /**
     * 使用者ID，表示這個商品喜歡屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 商品ID，表示這個商品喜歡屬於哪一個商品
     */
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductLikesId other = (ProductLikesId) obj;
        return Objects.equals(userId, other.userId) && Objects.equals(productId, other.productId);
    }
}
