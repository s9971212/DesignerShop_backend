package com.designershop.admin.coupons.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Getter
@Setter
public class AdminCreateCouponIssuanceRequestModel {

    @NotEmpty
    private List<String> userIds;
}
