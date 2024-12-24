package com.designershop.admin.coupons.models;

import jakarta.validation.constraints.NotBlank;
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
public class AdminUpdateCouponRequestModel {

    private String code;

    private String description;

    private String image;

    @NotBlank
    private String discountType;

    @NotBlank
    private String discountValue;

    private String minimumOrderPrice;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    private String issuanceLimit;

    private String usageLimit;

    @NotBlank
    private String isActive;

    private List<String> userIds;

    private List<String> categoryIds;

    private List<String> brandIds;

    private List<String> productIds;
}
