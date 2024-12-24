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
 * @version 1.0
 * @date 2024/12/22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_likes")
public class ProductLikes {

    /**
     * 商品喜歡ID，包含使用者ID與商品ID
     */
    @EmbeddedId
    private ProductLikesId id;

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
        ProductLikes other = (ProductLikes) obj;
        return Objects.equals(id, other.id);
    }
}
