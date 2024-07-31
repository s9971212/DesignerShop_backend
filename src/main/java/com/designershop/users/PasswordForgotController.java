package com.designershop.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.UserException;
import com.designershop.users.models.PasswordForgotRequestModel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/password_forgot")
@RequiredArgsConstructor
public class PasswordForgotController {

	private final PasswordForgotService passwordForgotService;

	// TODO 傳送email的API

	@PostMapping
	public ResponseEntity<String> passwordForgot(@RequestParam String token,
			@RequestBody PasswordForgotRequestModel request) throws UserException {
		String account = passwordForgotService.passwordForgot(token, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}
