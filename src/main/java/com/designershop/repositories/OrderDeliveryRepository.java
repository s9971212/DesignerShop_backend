package com.designershop.repositories;

import com.designershop.entities.CartItem;
import com.designershop.entities.OrderDelivery;
import com.designershop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Long> {

    @Query(value = "SELECT * FROM order_delivery WHERE is_default = 1 AND user_id =:userId", nativeQuery = true)
    OrderDelivery findByIsDefaultAndUserId(@Param("userId") String userId);
}
