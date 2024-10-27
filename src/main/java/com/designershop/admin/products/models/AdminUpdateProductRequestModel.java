package com.designershop.admin.products.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminUpdateProductRequestModel {

    @NotBlank
    private String category;

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String price;

    @NotBlank
    private String stockQuantity;

    @NotBlank
    private String isDeleted;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String termsCheckBox;
}
