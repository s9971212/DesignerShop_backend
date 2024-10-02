package com.designershop.repositories;

import com.designershop.entities.OrderDelivery;
import com.designershop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Long> {

}
