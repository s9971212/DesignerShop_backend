package com.designershop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponProductCategoryId implements Serializable {

    /**
     * 優惠券ID，表示這個優惠券商品類別限定屬於哪一個優惠券
     */
    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    /**
     * 商品類別ID，表示這個優惠券商品類別限定屬於哪一個商品類別
     */
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Override
    public int hashCode() {
        return Objects.hash(couponId, categoryId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponProductCategoryId other = (CouponProductCategoryId) obj;
        return Objects.equals(couponId, other.couponId) && Objects.equals(categoryId, other.categoryId);
    }
}
