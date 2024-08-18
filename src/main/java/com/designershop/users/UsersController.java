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

import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.ReadUserResponseModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestModel request)
			throws EmptyException, UserException, MessagingException {
		String account = usersService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReadUserResponseModel> readUser(@PathVariable String id) throws UserException {
		ReadUserResponseModel response = usersService.readUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable String id,
			@RequestBody @Valid UpdateUserRequestModel request) throws EmptyException, UserException {
		String account = usersService.updateUser(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable String id,
			@RequestBody @Valid UpdatePasswordRequestModel request)
			throws EmptyException, UserException, MessagingException {
		String account = usersService.updatePassword(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) throws UserException, MessagingException {
		String account = usersService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}
