package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
@Table(name = "product_category")
public class ProductCategory {

    /**
     * 商品類別ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    /**
     * 商品類別名稱
     */
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    /**
     * 商品類別中的所有商品
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory")
    private List<Product> products;

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductCategory other = (ProductCategory) obj;
        return Objects.equals(categoryId, other.categoryId) && Objects.equals(category, other.category);
    }
}
