package com.designershop.admin.users.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdatePasswordRequestModel {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
}
