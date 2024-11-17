package com.designershop.repositories;

import com.designershop.entities.ProductCategory;
import com.designershop.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT * FROM product_category WHERE category =:category", nativeQuery = true)
    ProductCategory findByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM product_category WHERE category_id IN (:categoryIds)", nativeQuery = true)
    List<ProductCategory> findByCategoryIds(@Param("categoryIds") List<String> categoryIds);
}
