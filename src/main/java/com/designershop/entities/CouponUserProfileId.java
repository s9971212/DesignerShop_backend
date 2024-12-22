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
public class CouponUserProfileId implements Serializable {

    /**
     * 優惠券ID，表示這個優惠券使用者限定屬於哪一個優惠券
     */
    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    /**
     * 使用者ID，表示這個優惠券使用者限定屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    @Override
    public int hashCode() {
        return Objects.hash(couponId, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponUserProfileId other = (CouponUserProfileId) obj;
        return Objects.equals(couponId, other.couponId) && Objects.equals(userId, other.userId);
    }
}
