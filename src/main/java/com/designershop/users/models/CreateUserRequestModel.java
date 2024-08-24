package com.designershop.users.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestModel {

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^09\\d{8}$")
    private String phoneNo;

    private String userName;

    private String gender;

    private String birthday;

    private String idCardNo;

    private String homeNo;

    private String userImage;

    @NotBlank
    private String termsCheckBox;
}
