package com.designershop.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequestModel {

	private String oldPassword;
	
	private String password;

	private String passwordCheck;
}
