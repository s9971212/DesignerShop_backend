package com.designershop.orders.models;

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
public class ReadOrderResponseModel {

    @NotBlank
    private String orderId;

    @NotBlank
    private String totalPrice;

    @NotBlank
    private String fullAddress;

    @NotBlank
    private String contactPhone;

    @NotBlank
    private String contactName;

    @NotEmpty
    private List<ReadOrderItemResponseModel> orderItems;
}
