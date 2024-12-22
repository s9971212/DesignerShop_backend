package com.designershop.users.models;

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
public class PasswordForgotRequestModel {

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
}
