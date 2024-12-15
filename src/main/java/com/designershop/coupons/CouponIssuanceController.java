package com.designershop.coupons;

import com.designershop.coupons.models.ReadCouponIssuanceResponseModel;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon_issuance")
@RequiredArgsConstructor
public class CouponIssuanceController {

    private final CouponIssuanceService couponIssuanceService;

    @PostMapping("/{couponId}")
    public ResponseEntity<String> createCouponIssuance(@PathVariable String couponId)
            throws UserException, CouponException {
        String code = couponIssuanceService.createCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping("/all/{couponId}")
    public ResponseEntity<List<ReadCouponIssuanceResponseModel>> readAllCouponIssuance(@PathVariable String couponId) throws UserException {
        List<ReadCouponIssuanceResponseModel> response = couponIssuanceService.readAllCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCouponIssuanceResponseModel> readCouponIssuance(@PathVariable String id) throws UserException,CouponException {
        ReadCouponIssuanceResponseModel response = couponIssuanceService.readCouponIssuance(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
