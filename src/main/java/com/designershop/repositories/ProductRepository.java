package com.designershop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designershop.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product WHERE user_id =:userId", nativeQuery = true)
    List<Product> findAllByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM product WHERE product_id =:productId", nativeQuery = true)
    Product findByProductId(@Param("productId") String productId);
}
