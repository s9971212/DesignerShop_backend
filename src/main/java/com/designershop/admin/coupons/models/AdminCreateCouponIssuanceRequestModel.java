package com.designershop.admin.coupons.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Getter
@Setter
public class AdminCreateCouponIssuanceRequestModel {

    @NotEmpty
    private List<String> userIds;
}
