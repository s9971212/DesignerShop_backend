package com.designershop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorSourceEunm {

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
