package com.designershop.coupons;

import com.designershop.admin.coupons.models.AdminCreateCouponRequestModel;
import com.designershop.admin.coupons.models.AdminReadCouponResponseModel;
import com.designershop.admin.coupons.models.AdminUpdateCouponRequestModel;
import com.designershop.coupons.models.CreateCouponRequestModel;
import com.designershop.coupons.models.ReadCouponResponseModel;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.ReadProductResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller/coupons")
@RequiredArgsConstructor
public class SellerCouponsController {

    private final SellerCouponsService sellerCouponsService;

    @PostMapping
    public ResponseEntity<String> createCoupon(@RequestBody @Valid CreateCouponRequestModel request) throws EmptyException, UserException,CouponException {
        String code = sellerCouponsService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(code);
    }

    @GetMapping
    public ResponseEntity<List<ReadCouponResponseModel>> readAllCoupon() throws UserException{
        List<ReadCouponResponseModel> response = sellerCouponsService.readAllCoupon();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCouponResponseModel> readCoupon(@PathVariable String id) throws UserException,CouponException {
        ReadCouponResponseModel response = sellerCouponsService.readCoupon(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
