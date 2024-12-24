package com.designershop.repositories;

import com.designershop.entities.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    @Query(value = "SELECT COUNT(*) FROM coupon_usages WHERE coupon_id =:couponId", nativeQuery = true)
    Long findCountByCouponId(@Param("couponId") int couponId);
}
