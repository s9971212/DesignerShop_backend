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
public class ReadOrderItemResponseModel {

    @NotBlank
    private String itemId;

    @NotBlank
    private String priceAtPurchase;

    @NotBlank
    private String quantity;

    @NotBlank
    private String productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String price;

    @NotBlank
    private String originalPrice;

    @NotBlank
    private String image;
}
