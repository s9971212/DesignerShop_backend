package com.designershop.repositories;

import com.designershop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id =:userId", nativeQuery = true)
    Cart findByUserId(@Param("userId") String userId);
}
