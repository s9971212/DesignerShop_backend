package com.designershop.repositories;

import com.designershop.entities.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {

    @Query(value = "SELECT * FROM product_brand WHERE LOWER(brand) LIKE LOWER(CONCAT('%', :brand, '%'))", nativeQuery = true)
    ProductBrand findByBrandIgnoreCase(@Param("brand") String brand);
}
