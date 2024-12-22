package com.designershop.admin.coupons;

import com.designershop.admin.coupons.models.*;
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
public class AdminCouponIssuanceController {

    private final AdminCouponIssuanceService adminCouponIssuanceService;

    @PostMapping("/{couponId}")
    public ResponseEntity<String> createCouponIssuance(@PathVariable String couponId, @RequestBody @Valid AdminCreateCouponIssuanceRequestModel request)
            throws CouponException {
        String code = adminCouponIssuanceService.createCouponIssuance(couponId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping("/all/{couponId}")
    public ResponseEntity<List<AdminReadCouponIssuanceResponseModel>> readAllCouponIssuance(@PathVariable String couponId) {
        List<AdminReadCouponIssuanceResponseModel> response = adminCouponIssuanceService.readAllCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminReadCouponIssuanceResponseModel> readCouponIssuance(@PathVariable String id) throws CouponException {
        AdminReadCouponIssuanceResponseModel response = adminCouponIssuanceService.readCouponIssuance(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
