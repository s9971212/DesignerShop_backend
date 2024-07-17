package com.designershop.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.entities.UserProfile;
import com.designershop.exceptions.UserException;
import com.designershop.users.models.RegisterRequestModel;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequestModel request) throws UserException {
		String account = usersService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}
