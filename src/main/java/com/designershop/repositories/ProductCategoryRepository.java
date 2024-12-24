package com.designershop.repositories;

import com.designershop.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT * FROM product_category WHERE category_id =:categoryId", nativeQuery = true)
    ProductCategory findByCategoryId(@Param("categoryId") int categoryId);

    @Query(value = "SELECT * FROM product_category WHERE category_id IN (:categoryIds)", nativeQuery = true)
    List<ProductCategory> findByCategoryIds(@Param("categoryIds") List<String> categoryIds);
}
