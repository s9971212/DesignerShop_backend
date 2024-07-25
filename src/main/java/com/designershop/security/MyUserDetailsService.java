package com.designershop.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

	private final UserProfileRepository userProfileRepository;

	@Override
	public MyUser loadUserByUsername(String username) throws UsernameNotFoundException {
		UserProfile userProfile = userProfileRepository.findByLogin(username);
		return new MyUser(userProfile);
	}
}
