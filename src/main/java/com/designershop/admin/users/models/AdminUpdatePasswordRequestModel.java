package com.designershop.admin.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdatePasswordRequestModel {

	private String oldPassword;
	
	private String password;

	private String passwordCheck;
}
