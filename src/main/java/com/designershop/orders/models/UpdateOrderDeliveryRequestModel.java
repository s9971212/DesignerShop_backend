package com.designershop.orders.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Getter
@Setter
public class UpdateOrderDeliveryRequestModel {

    @NotBlank
    private String address;

    private String district;

    private String city;

    private String state;

    private String postalCode;

    @NotBlank
    private String nation;

    @NotBlank
    private String contactPhone;

    @NotBlank
    private String contactName;

    @NotBlank
    private String defaultCheckBox;
}
