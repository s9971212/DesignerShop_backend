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
@Table(name = "coupon_usages")
public class CouponUsage {

    /**
     * 優惠券使用記錄ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id", nullable = false)
    private int usageId;

    /**
     * 優惠券使用時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "used_date", nullable = false)
    private LocalDateTime usedDate;

    /**
     * 使用者ID，表示這個優惠券使用記錄屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 訂單ID，表示這個優惠券使用記錄屬於哪一個訂單
     */
    @Column(name = "order_id", nullable = false, length = 14)
    private String orderId;

    /**
     * 該優惠券使用記錄所屬的優惠券
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Override
    public int hashCode() {
        return Objects.hash(usageId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponUsage other = (CouponUsage) obj;
        return Objects.equals(usageId, other.usageId);
    }
}
