package com.designershop.users.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgotSendEmailRequestModel {

    @NotBlank
    private String email;
}
