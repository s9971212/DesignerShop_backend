package com.designershop.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.UserException;
import com.designershop.users.models.UpdateRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping("/update")
	public ResponseEntity<UpdateUserRequestModel> update(@RequestBody UpdateRequestModel request) throws UserException {
		UpdateUserRequestModel response = usersService.update(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequestModel request) throws UserException {
		String account = usersService.updateUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}
