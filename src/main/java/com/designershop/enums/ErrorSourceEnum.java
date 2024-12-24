package com.designershop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
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
