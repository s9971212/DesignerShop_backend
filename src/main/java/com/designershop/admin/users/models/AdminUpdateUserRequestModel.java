package com.designershop.admin.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateUserRequestModel {

	private String userId;

	private String userType;

	private String sellerType;

	private String designerType;

	private String adminType;

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
