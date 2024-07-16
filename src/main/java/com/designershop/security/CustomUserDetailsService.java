package com.designershop.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		
		UserDetails user = User.builder().username("user").password("{noop}password") // {noop} 表示不使用密碼編碼器
				.roles("USER") // 分配 USER 角色
				.build();
		return user;
	}
}
