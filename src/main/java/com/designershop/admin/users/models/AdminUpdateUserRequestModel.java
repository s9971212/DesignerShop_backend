package com.designershop.admin.users.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateUserRequestModel {

    @NotBlank
    private String userType;

    private String sellerType;

    private String designerType;

    private String adminType;

    @NotBlank
    private String account;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^09\\d{8}$")
    private String phoneNo;

    private String name;

    private String gender;

    private String birthday;

    private String idCardNo;

    private String homeNo;

    private String image;

    @NotBlank
    private String isDeleted;

    @NotBlank
    private String termsCheckBox;
}
