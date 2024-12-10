package com.designershop.repositories;

import com.designershop.entities.Coupon;
import com.designershop.entities.CouponIssuance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {

}
