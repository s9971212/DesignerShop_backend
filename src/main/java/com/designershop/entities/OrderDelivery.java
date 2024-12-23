package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
@Table(name = "order_deliveries")
public class OrderDelivery {

    /**
     * 訂單配送ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", nullable = false)
    private int deliveryId;

    /**
     * 訂單配送的完整地址
     */
    @Column(name = "full_address", nullable = false, length = 1024)
    private String fullAddress;

    /**
     * 地址中的具體部分，例如街道名稱、門牌號碼等
     */
    @Column(name = "address", nullable = false, length = 256)
    private String address;

    /**
     * 地址中的區域或鄉鎮
     */
    @Column(name = "district", length = 100)
    private String district;

    /**
     * 地址中的城市
     */
    @Column(name = "city", length = 100)
    private String city;

    /**
     * 地址中的州或省
     */
    @Column(name = "state", length = 100)
    private String state;

    /**
     * 地址中的郵遞區號
     */
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    /**
     * 地址所在的國家
     */
    @Column(name = "nation", nullable = false, length = 100)
    private String nation;

    /**
     * 訂單配送聯絡電話
     */
    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    /**
     * 訂單配送聯絡人姓名
     */
    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    /**
     * 訂單配送是否為默認地址
     */
    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    /**
     * 使用者ID，表示這個訂單配送屬於哪一位使用者
     */
    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

    /**
     * 訂單配送中的所有訂單
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDelivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderDelivery other = (OrderDelivery) obj;
        return Objects.equals(deliveryId, other.deliveryId);
    }

    @PrePersist
    @PreUpdate
    public void handleEmptyStrings() {
        if (StringUtils.isBlank(this.district)) {
            this.district = null;
        }
        if (StringUtils.isBlank(this.city)) {
            this.city = null;
        }
        if (StringUtils.isBlank(this.state)) {
            this.state = null;
        }
        if (StringUtils.isBlank(this.postalCode)) {
            this.postalCode = null;
        }
    }
}
