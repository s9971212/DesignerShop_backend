package com.designershop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query(value = "SELECT * FROM product_image WHERE product_id =:productId", nativeQuery = true)
    List<ProductImage> findAllByProductId(@Param("productId") String productId);
}
