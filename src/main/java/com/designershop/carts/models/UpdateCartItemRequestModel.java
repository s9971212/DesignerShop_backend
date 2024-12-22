package com.designershop.carts.models;

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
public class UpdateCartItemRequestModel {

    @NotBlank
    private String quantity;
}
