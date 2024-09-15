package com.designershop.repositories;

import com.designershop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1", nativeQuery = true)
    Order findMaxOrderId();
}
