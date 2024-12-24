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
@Table(name = "product_brand")
public class ProductBrand {

    /**
     * 商品品牌ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    private int brandId;

    /**
     * 商品品牌名稱
     */
    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    /**
     * 商品品牌中的所有商品
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productBrand")
    private List<Product> products;

    @Override
    public int hashCode() {
        return Objects.hash(brandId, brand);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductBrand other = (ProductBrand) obj;
        return Objects.equals(brandId, other.brandId) && Objects.equals(brand, other.brand);
    }
}
