package com.designershop.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.UserException;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody CreateUserRequestModel request) throws UserException {
		String account = usersService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UpdateUserRequestModel> readUser(@PathVariable String id) throws UserException {
		UpdateUserRequestModel response = usersService.readUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UpdateUserRequestModel request)
			throws UserException {
		String account = usersService.updateUser(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable String id,
			@RequestBody UpdatePasswordRequestModel request) throws UserException {
		String account = usersService.updatePassword(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) throws UserException {
		String account = usersService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}
