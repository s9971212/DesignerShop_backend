package com.designershop.repositories;

import com.designershop.entities.Coupon;
import com.designershop.entities.CouponUserProfile;
import com.designershop.entities.CouponUserProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUserProfileRepository extends JpaRepository<CouponUserProfile, CouponUserProfileId> {

}
