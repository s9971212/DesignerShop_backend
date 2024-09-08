package com.designershop.repositories;

import com.designershop.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "SELECT * FROM cart_item WHERE cart_id =:cartId AND product_id =:productId", nativeQuery = true)
    CartItem findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") String productId);

    @Query(value = "SELECT * FROM cart_item WHERE cart_id =:cartId", nativeQuery = true)
    List<CartItem> findAllByCartId(@Param("cartId") int cartId);
}