package com.designershop.entities;

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
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "items_id", nullable = false)
    private int itemsId;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Override
    public int hashCode() {
        return Objects.hash(itemsId, productId, cart != null ? cart.getCartId() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        return Objects.equals(itemsId, other.itemsId) && Objects.equals(productId, other.productId) &&
                Objects.equals(cart != null ? cart.getCartId() : null, other.cart != null ? other.cart.getCartId() : null);
    }
}
