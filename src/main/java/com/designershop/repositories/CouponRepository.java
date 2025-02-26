package com.designershop.repositories;

import com.designershop.entities.Coupon;
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
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT * FROM coupons WHERE code =:code", nativeQuery = true)
    Coupon findByCode(@Param("code") String code);

    @Query(value = "SELECT * FROM coupons WHERE coupon_id =:couponId", nativeQuery = true)
    Coupon findByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupons WHERE coupon_id IN (:couponIds)", nativeQuery = true)
    List<Coupon> findByCouponIds(@Param("couponIds") List<Integer> couponIds);
}
