package com.designershop.admin.coupons.models;

import jakarta.validation.constraints.NotBlank;
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
public class AdminReadCouponResponseModel {

    @NotBlank
    private String couponId;

    private String code;

    @NotBlank
    private String discountType;

    @NotBlank
    private String discountValue;

    private String minimumOrderPrice;

    private String issuanceLimit;

    private String usageLimit;

    private String description;

    private String image;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @NotBlank
    private String createdDate;

    @NotBlank
    private String isActive;

    private List<String> userIds;

    private List<String> categoryIds;

    private List<String> brandIds;

    private List<String> productIds;
}
