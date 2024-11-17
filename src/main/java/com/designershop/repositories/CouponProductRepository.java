package com.designershop.repositories;

import com.designershop.entities.CouponProduct;
import com.designershop.entities.CouponProductBrand;
import com.designershop.entities.CouponProductBrandId;
import com.designershop.entities.CouponProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponProductRepository extends JpaRepository<CouponProduct, CouponProductId> {

}
