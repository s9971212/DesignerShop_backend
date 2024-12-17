package com.designershop.admin.products.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminCreateProductRequestModel {

    @NotBlank
    private String categoryId;

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String price;

    @NotBlank
    private String stockQuantity;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String termsCheckBox;
}
