package com.designershop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    // 待付款
    PENDING,
    // 已付款
    PAID,
    // 失敗
    FAILED,
    // 已發貨
    SHIPPED
}
