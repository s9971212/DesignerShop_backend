package com.designershop.coupons;

import com.designershop.admin.coupons.AdminCouponIssuancesService;
import com.designershop.admin.coupons.models.AdminCreateCouponIssuanceRequestModel;
import com.designershop.admin.coupons.models.AdminReadCouponIssuanceResponseModel;
import com.designershop.coupons.models.ReadCouponIssuanceResponseModel;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon_issuance")
@RequiredArgsConstructor
public class CouponIssuancesController {

    private final CouponIssuancesService couponIssuancesService;

    @PostMapping("/{couponId}")
    public ResponseEntity<String> createCouponIssuance(@PathVariable String couponId)
            throws UserException, CouponException {
        String code = couponIssuancesService.createCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping("/all/{couponId}")
    public ResponseEntity<List<ReadCouponIssuanceResponseModel>> readAllCouponIssuance(@PathVariable String couponId) throws UserException {
        List<ReadCouponIssuanceResponseModel> response = couponIssuancesService.readAllCouponIssuance(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCouponIssuanceResponseModel> readCouponIssuance(@PathVariable String id) throws UserException,CouponException {
        ReadCouponIssuanceResponseModel response = couponIssuancesService.readCouponIssuance(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
