package com.designershop.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestModel {

	private String account;

	private String password;

	private String passwordCheck;

	private String email;

	private String phoneNo;

	private String userName;

	private String gender;

	private String birthday;

	private String idCardNo;

	private String homeNo;

	private String userPhoto;

	private String termsCheckBox;
}
