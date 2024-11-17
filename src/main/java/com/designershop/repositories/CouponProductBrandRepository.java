package com.designershop.repositories;

import com.designershop.entities.CouponProductBrand;
import com.designershop.entities.CouponProductBrandId;
import com.designershop.entities.CouponProductCategory;
import com.designershop.entities.CouponProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponProductBrandRepository extends JpaRepository<CouponProductBrand, CouponProductBrandId> {

}
