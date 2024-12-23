package com.designershop.coupons;

import com.designershop.coupons.models.ReadCouponIssuanceResponseModel;
import com.designershop.exceptions.CouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@RestController
@RequestMapping("/api/coupon_issuance")
@RequiredArgsConstructor
public class CouponIssuanceController {

    private final CouponIssuanceService couponIssuanceService;

    @PostMapping("/{couponId}")
    public ResponseEntity<String> createCouponIssuance(@PathVariable String couponId)
            throws CouponException {
        String code = couponIssuanceService.createCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping("/all/{couponId}")
    public ResponseEntity<List<ReadCouponIssuanceResponseModel>> readAllCouponIssuance(@PathVariable String couponId) throws CouponException {
        List<ReadCouponIssuanceResponseModel> response = couponIssuanceService.readAllCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCouponIssuanceResponseModel> readCouponIssuance(@PathVariable String id) throws CouponException {
        ReadCouponIssuanceResponseModel response = couponIssuanceService.readCouponIssuance(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
