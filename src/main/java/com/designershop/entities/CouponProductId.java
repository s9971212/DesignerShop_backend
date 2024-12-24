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
 * @version 1.0
 * @date 2024/12/22
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponProductId implements Serializable {

    /**
     * 優惠券ID，表示這個優惠券商品限定屬於哪一個優惠券
     */
    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    /**
     * 商品ID，表示這個優惠券商品限定屬於哪一個商品
     */
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Override
    public int hashCode() {
        return Objects.hash(couponId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponProductId other = (CouponProductId) obj;
        return Objects.equals(couponId, other.couponId) && Objects.equals(productId, other.productId);
    }
}
