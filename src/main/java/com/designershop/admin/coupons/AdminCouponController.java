package com.designershop.admin.coupons;

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
@RequestMapping("/admin/coupon")
@RequiredArgsConstructor
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @PostMapping
    public ResponseEntity<String> createCoupon(@RequestBody @Valid AdminCreateCouponRequestModel request)
            throws EmptyException, CouponException {
        String code = adminCouponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping
    public ResponseEntity<List<AdminReadCouponResponseModel>> readAllCoupon() {
        List<AdminReadCouponResponseModel> response = adminCouponService.readAllCoupon();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminReadCouponResponseModel> readCoupon(@PathVariable String id) throws CouponException {
        AdminReadCouponResponseModel response = adminCouponService.readCoupon(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoupon(@PathVariable String id, @RequestBody @Valid AdminUpdateCouponRequestModel request)
            throws EmptyException, CouponException {
        String code = adminCouponService.updateCoupon(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }
}
