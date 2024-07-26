package com.designershop.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtTokenExpirationListener {

	@EventListener
	public void handleTokenExpiredEvent(AuthenticationFailureExpiredEvent event) {
		Authentication authentication = event.getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof MyUser) {
			MyUser myUser = (MyUser) authentication.getPrincipal();

			HttpSession session = getSessionForUser(myUser.getUsername());
			if (session != null) {
				session.removeAttribute("userProfile");
				session.invalidate();
			}
		}
	}

	private HttpSession getSessionForUser(String username) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request.getSession(false);
	}
}
