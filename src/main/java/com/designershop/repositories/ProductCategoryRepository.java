package com.designershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT * FROM product_category WHERE category_name =:categoryName", nativeQuery = true)
    ProductCategory findByCategoryName(@Param("categoryName") String categoryName);
}
