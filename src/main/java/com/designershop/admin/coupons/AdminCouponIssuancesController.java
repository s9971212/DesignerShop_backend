package com.designershop.admin.coupons;

import com.designershop.admin.coupons.models.AdminCreateCouponIssuanceRequestModel;
import com.designershop.admin.coupons.models.AdminCreateCouponRequestModel;
import com.designershop.admin.coupons.models.AdminReadCouponResponseModel;
import com.designershop.admin.coupons.models.AdminUpdateCouponRequestModel;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.EmptyException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/coupon_issuance")
@RequiredArgsConstructor
public class AdminCouponIssuancesController {

    private final AdminCouponIssuancesService adminCouponIssuancesService;

    @PostMapping("/{couponId}")
    public ResponseEntity<String> createCouponIssuance(@PathVariable String couponId, @RequestBody @Valid AdminCreateCouponIssuanceRequestModel request)
            throws EmptyException, CouponException {
        String code = adminCouponIssuancesService.createCouponIssuance(couponId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }
}
