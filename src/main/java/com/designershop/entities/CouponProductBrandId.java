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
public class CouponProductBrandId implements Serializable {

    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    @Column(name = "brand_id", nullable = false)
    private int brandId;

    @Override
    public int hashCode() {
        return Objects.hash(couponId, brandId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponProductBrandId other = (CouponProductBrandId) obj;
        return Objects.equals(couponId, other.couponId) && Objects.equals(brandId, other.brandId);
    }
}
