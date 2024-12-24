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

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Component
@RequiredArgsConstructor
public class MyLogoutHandler implements LogoutHandler {

    private final HttpSession session;
    private final UserProfileRepository userProfileRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.nonNull(userProfile)) {
            userProfile.setSignOnToken(null);
            userProfile.setSignOnStatus("N");
            userProfile.setPwdErrorCount(0);
            userProfileRepository.save(userProfile);
        }
    }
}
