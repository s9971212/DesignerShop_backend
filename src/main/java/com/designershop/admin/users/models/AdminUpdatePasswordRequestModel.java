package com.designershop.admin.users.models;

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
public class AdminUpdatePasswordRequestModel {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
}
