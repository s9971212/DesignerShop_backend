package com.designershop.carts.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestModel {

    @NotBlank
    private String quantity;
}
