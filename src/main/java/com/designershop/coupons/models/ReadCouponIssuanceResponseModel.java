package com.designershop.coupons.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Getter
@Setter
public class ReadCouponIssuanceResponseModel {

    @NotBlank
    private String issuanceId;

    @NotBlank
    private String isUsed;

    private String usedDate;
}
