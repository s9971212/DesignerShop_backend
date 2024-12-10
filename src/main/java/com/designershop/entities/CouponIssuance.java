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
@Table(name = "coupon_issuance")
public class CouponIssuance {

    /**
     * 優惠券發放記錄ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuance_id", nullable = false)
    private int issuanceId;

    /**
     * 優惠券是否已被使用
     */
    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    /**
     * 優惠券使用時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "used_date")
    private LocalDateTime usedDate;

    /**
     * 使用者ID，表示這個優惠券發放記錄屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 該優惠券發放記錄所屬的優惠券
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Override
    public int hashCode() {
        return Objects.hash(issuanceId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CouponIssuance other = (CouponIssuance) obj;
        return Objects.equals(issuanceId, other.issuanceId);
    }
}
