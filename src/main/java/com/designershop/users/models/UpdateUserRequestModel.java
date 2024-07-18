package com.designershop.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestModel {

	private String userId;

	private String account;

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
