package com.designershop.repositories;

import com.designershop.entities.Product;
import com.designershop.entities.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE user_id =:userId", nativeQuery = true)
    List<Product> findAllByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM products WHERE product_id =:productId", nativeQuery = true)
    Product findByProductId(@Param("productId") int productId);

    @Query(value = "SELECT * FROM products WHERE product_id IN (:productIds)", nativeQuery = true)
    List<Product> findByProductIds(@Param("productIds") List<String> productIds);
}
