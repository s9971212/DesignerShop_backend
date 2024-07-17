package com.designershop.users;

import org.springframework.stereotype.Service;
import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UserProfileRepository userProfileRepository;

	public UserProfile test() {
		String userId = "T236";
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		return userProfile;
	}
}
