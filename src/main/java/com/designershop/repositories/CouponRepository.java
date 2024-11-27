package com.designershop.repositories;

import com.designershop.entities.Coupon;
import com.designershop.entities.OrderItem;
import com.designershop.entities.Product;
import com.designershop.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT * FROM coupons WHERE coupon_id =:couponId", nativeQuery = true)
    Coupon findByCouponId(@Param("couponId") int couponId);
}
