package com.designershop.products.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Getter
@Setter
public class ReadProductResponseModel {

    @NotBlank
    private String productId;

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
}
