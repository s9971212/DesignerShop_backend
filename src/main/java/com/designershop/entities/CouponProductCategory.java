package com.designershop.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "coupons_product_category")
public class CouponProductCategory {

    /**
     * 優惠券商品類別限定ID，包含優惠券ID與商品類別ID
     */
    @EmbeddedId
    private CouponProductCategoryId id;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponProductCategory other = (CouponProductCategory) obj;
        return Objects.equals(id, other.id);
    }
}
