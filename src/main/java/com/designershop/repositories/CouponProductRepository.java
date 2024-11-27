package com.designershop.repositories;

import com.designershop.entities.CouponProduct;
import com.designershop.entities.CouponProductBrand;
import com.designershop.entities.CouponProductBrandId;
import com.designershop.entities.CouponProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponProductRepository extends JpaRepository<CouponProduct, CouponProductId> {

    @Query(value = "SELECT * FROM coupons_products WHERE coupon_id =:couponId", nativeQuery = true)
    List<CouponProduct> findAllByCouponId(@Param("couponId") int couponId);

    @Query(value = "SELECT * FROM coupons_products WHERE coupon_id =:couponId AND product_id =:productId", nativeQuery = true)
    CouponProduct findByCouponIdAndProductId(@Param("couponId") int couponId, @Param("productId") int productId);
}
