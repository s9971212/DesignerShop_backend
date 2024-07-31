package com.designershop.security;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.PasswordExpiredException;
import com.designershop.exceptions.UserException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JwtAuthController {

	private final JwtAuthService jwtAuthService;

	@PostMapping
	public ResponseEntity<String> auth(@RequestBody Map<String, Object> request) throws UserException {
		try {
			String token = jwtAuthService.auth(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(token);
		} catch (PasswordExpiredException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("PasswordExpired");
		}
	}

	@ExceptionHandler(PasswordExpiredException.class)
	public ResponseEntity<String> handlePasswordExpiredException(PasswordExpiredException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}
}
