package com.designershop.repositories;

import com.designershop.entities.CouponIssuance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Repository
public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {

    @Query(value = "SELECT * FROM coupon_issuance WHERE user_id =:userId AND coupon_id =:couponId", nativeQuery = true)
    List<CouponIssuance> findByUserIdAndCouponId(@Param("userId") String userId, @Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupon_issuance WHERE coupon_id =:couponId", nativeQuery = true)
    List<CouponIssuance> findAllByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupon_issuance WHERE issuance_id =:issuanceId", nativeQuery = true)
    CouponIssuance findByIssuanceId(@Param("issuanceId") int issuanceId);

    @Query(value = "SELECT * FROM coupon_issuance WHERE issuance_id =:issuanceId AND user_id =:userId", nativeQuery = true)
    CouponIssuance findByIssuanceIdAndUserId(@Param("issuanceId") int issuanceId,@Param("userId") String userId);
}
