package com.designershop.users.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgotRequestModel {

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
}
