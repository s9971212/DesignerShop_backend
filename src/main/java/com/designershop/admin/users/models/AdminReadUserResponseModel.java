package com.designershop.admin.users.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminReadUserResponseModel {

    @NotBlank
    private String userId;

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

    private String userName;

    private String gender;

    private String birthday;

    private String idCardNo;

    private String homeNo;

    private String userImage;

    @NotBlank
    private String registerDate;

    private String pwdChangedDate;

    @NotBlank
    private String pwdExpireDate;

    @NotBlank
    private String isDeleted;
}
