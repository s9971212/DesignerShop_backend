package com.designershop.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.entities.UserProfile;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/error")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@PostMapping("/test")
	public UserProfile testController() {
		UserProfile userProfile = usersService.test();

		return userProfile;
	}
}
