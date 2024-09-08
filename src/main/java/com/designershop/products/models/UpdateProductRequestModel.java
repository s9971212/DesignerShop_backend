package com.designershop.products.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductRequestModel {

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
    private String stockQuantity;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String termsCheckBox;
}
