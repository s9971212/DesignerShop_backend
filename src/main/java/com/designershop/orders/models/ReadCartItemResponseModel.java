package com.designershop.orders.models;

import com.designershop.products.models.ReadProductResponseModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadCartItemResponseModel {

    @NotBlank
    private String productName;

    @NotBlank
    private String price;

    @NotBlank
    private String stockQuantity;

    @NotEmpty
    private List<String> images;

    @NotBlank
    private String quantity;
}
