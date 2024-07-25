package com.designershop.security;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.designershop.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

	private final AuthenticationManager authenticationManager;

	public String auth(Map<String, Object> request) {
		String username = (String) request.get("username");
		String password = (String) request.get("password");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		String token = JwtUtil.generateToken(authentication);

		return token;
	}
}
