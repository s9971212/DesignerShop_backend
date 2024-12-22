package com.designershop.repositories;

import com.designershop.entities.Coupon;
import com.designershop.entities.CouponUsage;
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
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {


}