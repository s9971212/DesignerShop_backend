package com.designershop.entities;

import com.designershop.enums.DiscountTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {

    /**
     * 優惠券ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private int couponId;

    /**
     * 優惠券的唯一代碼
     */
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * 優惠券的折扣類型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountTypeEnum discountType;

    /**
     * 優惠券的折扣值
     */
    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    /**
     * 使用優惠券所需的最低訂單價格
     */
    @Column(name = "minimum_order_price", precision = 10, scale = 2)
    private BigDecimal minimumOrderPrice;

    /**
     * 優惠券的發放次數限制
     */
    @Column(name = "issuance_limit")
    private Integer issuanceLimit;

    /**
     * 優惠券的使用次數限制
     */
    @Column(name = "usage_limit")
    private Integer usageLimit;

    /**
     * 優惠券描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * 優惠券圖片
     */
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    /**
     * 優惠券開始時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * 優惠券結束時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /**
     * 創建該優惠券的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * 上次更新該優惠券的使用者ID
     */
    @Column(name = "updated_user", length = 12)
    private String updatedUser;

    /**
     * 上次更新該優惠券的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * 優惠券是否啟用
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    /**
     * 優惠券中的所有優惠券發放記錄
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
    private List<CouponIssuance> couponIssuance;

    /**
     * 優惠券中的所有優惠券使用記錄
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
    private List<CouponUsage> couponUsages;

    @Override
    public int hashCode() {
        return Objects.hash(couponId, code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coupon other = (Coupon) obj;
        return Objects.equals(couponId, other.couponId) && Objects.equals(code, other.code);
    }

    @PrePersist
    @PreUpdate
    public void handleEmptyStrings() {
        if (StringUtils.isBlank(this.description)) {
            this.description = null;
        }
        if (StringUtils.isBlank(this.image)) {
            this.image = null;
        }
    }
}
