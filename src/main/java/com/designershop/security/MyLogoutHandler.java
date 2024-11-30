package com.designershop.security;

import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MyLogoutHandler implements LogoutHandler {

    private final HttpSession session;
    private final UserProfileRepository userProfileRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.nonNull(userProfile)) {
            userProfile.setSignOnStatus("N");
            userProfile.setPwdErrorCount(0);
            userProfile.setSignOnToken(null);
            userProfileRepository.save(userProfile);
        }
    }
}
