package com.designershop.repositories;

import com.designershop.entities.Coupon;
import com.designershop.entities.CouponUserProfile;
import com.designershop.entities.CouponUserProfileId;
import com.designershop.entities.Product;
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
public interface CouponUserProfileRepository extends JpaRepository<CouponUserProfile, CouponUserProfileId> {

    @Query(value = "SELECT * FROM coupons_user_profile WHERE coupon_id =:couponId", nativeQuery = true)
    List<CouponUserProfile> findAllByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupons_user_profile WHERE coupon_id =:couponId AND user_id =:userId", nativeQuery = true)
    CouponUserProfile findByCouponIdAndUserId(@Param("couponId") int couponId, @Param("userId") String userId);

    @Query(value = "SELECT * FROM coupons_user_profile WHERE user_id =:userId", nativeQuery = true)
    List<CouponUserProfile> findAllByUserId(@Param("userId") String userId);
}
