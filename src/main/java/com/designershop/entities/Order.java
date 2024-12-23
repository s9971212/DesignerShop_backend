package com.designershop.entities;

import com.designershop.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
@Table(name = "orders")
public class Order {

    /**
     * 訂單ID
     */
    @Id
    @Column(name = "order_id", nullable = false, length = 14)
    private String orderId;

    /**
     * 訂單總價
     */
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    /**
     * 創建該訂單的時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    /**
     * 訂單配送的完整地址
     */
    @Column(name = "full_address", nullable = false, length = 1024)
    private String fullAddress;

    /**
     * 訂單聯絡電話
     */
    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    /**
     * 訂單聯絡人姓名
     */
    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    /**
     * 訂單狀態
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.PENDING;

    /**
     * 使用者ID，表示這個訂單屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 該訂單所屬的訂單配送
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id")
    private OrderDelivery orderDelivery;

    /**
     * 訂單中的所有訂單項目
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return Objects.equals(orderId, other.orderId);
    }
}
