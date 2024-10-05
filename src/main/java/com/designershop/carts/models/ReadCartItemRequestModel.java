package com.designershop.carts.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadCartItemRequestModel {

    @NotEmpty
    private List<String> itemIds;
}
