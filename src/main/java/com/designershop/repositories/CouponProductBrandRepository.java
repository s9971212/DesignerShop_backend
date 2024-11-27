package com.designershop.repositories;

import com.designershop.entities.CouponProductBrand;
import com.designershop.entities.CouponProductBrandId;
import com.designershop.entities.CouponProductCategory;
import com.designershop.entities.CouponProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponProductBrandRepository extends JpaRepository<CouponProductBrand, CouponProductBrandId> {

    @Query(value = "SELECT * FROM coupons_product_brand WHERE coupon_id =:couponId", nativeQuery = true)
    List<CouponProductBrand> findAllByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupons_product_brand WHERE coupon_id =:couponId AND brand_id =:brandId", nativeQuery = true)
    CouponProductBrand findByCouponIdAndBrandId(@Param("couponId") int couponId, @Param("brandId") int brandId);
}
