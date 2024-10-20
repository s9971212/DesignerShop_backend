package com.designershop.repositories;

import com.designershop.entities.Cart;
import com.designershop.entities.Order;
import com.designershop.entities.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1", nativeQuery = true)
    Order findMaxOrderId();

    @Query(value = "SELECT * FROM orders WHERE user_id =:userId", nativeQuery = true)
    List<Order> findAllByUserId(@Param("userId") String userId);
}
