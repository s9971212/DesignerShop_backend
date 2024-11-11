package com.designershop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorSourceEnum {

    // Empty
    E,
    // User
    U,
    // Product
    PD,
    // Cart
    C,
    // Order
    OD,
    // Coupon
    CP
}
