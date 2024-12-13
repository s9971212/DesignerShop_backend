package com.designershop.coupons.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadCouponIssuanceResponseModel {

    @NotBlank
    private String issuanceId;

    @NotBlank
    private String isUsed;

    private String usedDate;
}
