package com.designershop.admin.products.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminReadProductResponseModel {

    @NotBlank
    private String productId;

    @NotBlank
    private String category;

    @NotBlank
    private String brand;

    @NotBlank
    private String productName;

    private String productDescription;

    @NotBlank
    private String price;

    @NotBlank
    private String originalPrice;

    @NotBlank
    private String stockQuantity;

    @NotBlank
    private String soldQuantity;

    @NotBlank
    private String likes;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String createdDate;

    @NotBlank
    private String isDeleted;
}
