package com.designershop.repositories;

import com.designershop.entities.CouponProductCategory;
import com.designershop.entities.CouponProductCategoryId;
import com.designershop.entities.CouponUserProfile;
import com.designershop.entities.CouponUserProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponProductCategoryRepository extends JpaRepository<CouponProductCategory, CouponProductCategoryId> {

}
