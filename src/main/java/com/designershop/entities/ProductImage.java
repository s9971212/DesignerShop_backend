package com.designershop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "product_images")
public class ProductImage {

    /**
     * 商品圖片ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private int imageId;

    /**
     * 商品圖片資料
     */
    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    /**
     * 該商品圖片所屬的商品
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductImage other = (ProductImage) obj;
        return Objects.equals(imageId, other.imageId);
    }
}
