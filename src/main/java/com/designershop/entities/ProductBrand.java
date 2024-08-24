package com.designershop.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_brand")
public class ProductBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    private int brandId;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

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
