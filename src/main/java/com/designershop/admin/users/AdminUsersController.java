package com.designershop.admin.users;

import java.util.List;

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

import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.exceptions.UserException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

	private final AdminUsersService adminUsersService;

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody AdminCreateUserRequestModel request) throws UserException {
		String account = adminUsersService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@GetMapping
	public ResponseEntity<List<AdminUpdateUserRequestModel>> readAllUser() {
		List<AdminUpdateUserRequestModel> response = adminUsersService.readAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdminUpdateUserRequestModel> readUser(@PathVariable String id) throws UserException {
		AdminUpdateUserRequestModel response = adminUsersService.readUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody AdminUpdateUserRequestModel request)
			throws UserException {
		String account = adminUsersService.updateUser(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<String> updatePassword(@PathVariable String id,
			@RequestBody AdminUpdatePasswordRequestModel request) throws UserException {
		String account = adminUsersService.updatePassword(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) throws UserException {
		String account = adminUsersService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}
}