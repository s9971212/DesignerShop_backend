package com.designershop.orders.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDeliveryRequestModel {

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
