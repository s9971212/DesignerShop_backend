package com.designershop.security;

import com.designershop.entities.UserProfile;
import com.designershop.exceptions.PasswordExpiredException;
import com.designershop.exceptions.UserException;
import com.designershop.mail.MailService;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final HttpSession session;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserProfileRepository userProfileRepository;
    private final JwtUtil jwtUtil;

    public String auth(Map<String, Object> request) throws UserException, PasswordExpiredException, MessagingException {
        String username = (String) request.get("username");
        String password = (String) request.get("password");

        UserProfile userProfile = userProfileRepository.findByLogin(username);
        if (Objects.isNull(userProfile)) {
            throw new UserException("登入失敗，請稍後再試或使用其他登入方法");
        }
        if (userProfile.isDeleted()) {
            throw new UserException("此帳戶已被刪除，請重新確認");
        }

        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();
        if (LocalDateTime.now().isAfter(userProfile.getPwdExpireDate())) {
            userProfile.setLock(true);
            userProfile.setLockDate(currentDateTime);
            userProfileRepository.save(userProfile);
            throw new PasswordExpiredException("密碼已過期，請修改密碼");
        }

        if (Objects.nonNull(userProfile.getUnlockDate())) {
            if (LocalDateTime.now().isAfter(userProfile.getUnlockDate())) {
                userProfile.setLock(false);
                userProfile.setLockDate(null);
                userProfile.setUnlockDate(null);
                userProfileRepository.save(userProfile);
            } else {
                throw new UserException("帳號已被鎖定，直到" + userProfile.getUnlockDate().format(DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            String token = jwtUtil.generateToken(authentication);

            userProfile.setSignOnToken(token);
            userProfile.setSignOnStatus("Y");
            userProfile.setPwdErrorCount(0);
            userProfileRepository.save(userProfile);
            session.setAttribute("userProfile", userProfile);

            String[] receivers = {userProfile.getEmail()};
            String[] cc = {};
            String[] bcc = {};
            Map<String, Object> map = new HashMap<>();
            map.put("email", userProfile.getEmail());
            map.put("account", userProfile.getAccount());
            mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 帳戶登入通知", "account-sign-on", map);
            return token;
        } catch (AuthenticationException e) {
            int pwdErrorCount = userProfile.getPwdErrorCount();
            pwdErrorCount++;
            userProfile.setPwdErrorCount(pwdErrorCount);
            userProfileRepository.save(userProfile);

            if (pwdErrorCount == 5) {
                userProfile.setPwdErrorCount(0);
                userProfile.setLock(true);
                userProfile.setLockDate(currentDateTime);
                userProfile.setUnlockDate(currentDateTime.plusMinutes(5));
                userProfileRepository.save(userProfile);
                throw new UserException("密碼輸入錯誤次數過多，帳號已被鎖定，直到" + currentDateTime.plusMinutes(5).format(DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }

            throw new UserException("你的帳號或密碼不正確，請再試一次");
        }
    }
}
