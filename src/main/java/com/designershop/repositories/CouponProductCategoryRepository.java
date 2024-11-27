package com.designershop.repositories;

import com.designershop.entities.CouponProductCategory;
import com.designershop.entities.CouponProductCategoryId;
import com.designershop.entities.CouponUserProfile;
import com.designershop.entities.CouponUserProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponProductCategoryRepository extends JpaRepository<CouponProductCategory, CouponProductCategoryId> {

    @Query(value = "SELECT * FROM coupons_product_category WHERE coupon_id =:couponId", nativeQuery = true)
    List<CouponProductCategory> findAllByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupons_product_category WHERE coupon_id =:couponId AND category_id =:categoryId", nativeQuery = true)
    CouponProductCategory findByCouponIdAndCategoryId(@Param("couponId") int couponId, @Param("categoryId") int categoryId);
}
