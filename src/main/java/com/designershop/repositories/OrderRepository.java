package com.designershop.repositories;

import com.designershop.entities.Order;
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
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1", nativeQuery = true)
    Order findMaxOrderId();

    @Query(value = "SELECT * FROM orders WHERE user_id =:userId", nativeQuery = true)
    List<Order> findAllByUserId(@Param("userId") String userId);
}
