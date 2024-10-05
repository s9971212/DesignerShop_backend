package com.designershop.carts.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadCartItemResponseModel {

    @NotBlank
    private String itemId;

    private String userName;

    @NotBlank
    private String productName;

    @NotBlank
    private String price;

    @NotBlank
    private String stockQuantity;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String isDeleted;

    @NotBlank
    private String quantity;
}