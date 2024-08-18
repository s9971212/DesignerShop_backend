package com.designershop.security;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyLogoutHandler implements LogoutHandler {

	private final HttpSession session;
	private final UserProfileRepository userProfileRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
		if (Objects.nonNull(sessionUserProfile)) {
			sessionUserProfile.setSignOnStatus("N");
			sessionUserProfile.setPwdErrorCount(0);
			sessionUserProfile.setSignOnToken(null);
			userProfileRepository.save(sessionUserProfile);
		}
	}
}
