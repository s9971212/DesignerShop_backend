package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_delivery")
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", nullable = false)
    private int deliveryId;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "district", length = 100)
    private String district;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "nation", nullable = false, length = 100)
    private String nation;

    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    @Column(name = "contact_name", nullable = false, length = 100)
    private String contactName;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Column(name = "user_id", nullable = false, length = 12)
    private String userId;

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
