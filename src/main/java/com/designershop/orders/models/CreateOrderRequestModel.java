package com.designershop.orders.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequestModel {

    @NotEmpty
    private List<String> itemIds;

    @NotEmpty
    private List<String> couponIds;
}
