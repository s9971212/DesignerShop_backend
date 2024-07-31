package com.designershop.users.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgotRequestModel {

	private String password;

	private String passwordCheck;
}
