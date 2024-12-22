package com.designershop.security;

import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtTokenExpirationListener {

    private final UserProfileRepository userProfileRepository;

    @EventListener
    public void handleTokenExpiredEvent(AuthenticationFailureExpiredEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof MyUser myUser) {
            HttpSession session = getSessionForUser(myUser.getUsername());
            if (session != null) {
                UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
                if (Objects.nonNull(userProfile)) {
                    userProfile.setSignOnStatus("N");
                    userProfile.setSignOnToken(null);
                    userProfileRepository.save(userProfile);
                    session.removeAttribute("userProfile");
                }

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
