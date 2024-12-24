package com.designershop.users;

import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.mail.MailService;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.models.PasswordForgotRequestModel;
import com.designershop.users.models.PasswordForgotSendEmailRequestModel;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.RandomTokenUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class PasswordForgotService {

    private final MailService mailService;
    private final UserProfileRepository userProfileRepository;

    public String passwordForgotSendEmail(PasswordForgotSendEmailRequestModel request) throws EmptyException, UserException, MessagingException {
        String email = request.getEmail();

        if (StringUtils.isBlank(email)) {
            throw new EmptyException("Email不得為空");
        }
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (Objects.isNull(userProfile)) {
            throw new UserException("email錯誤，請重新確認");
        }
        if (userProfile.isDeleted()) {
            throw new UserException("此帳戶已被刪除，請重新確認");
        }

        String token = RandomTokenUtil.randomTokenGenerate(100);
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

        userProfile.setPwdForgotToken(token);
        userProfile.setPwdForgotTokenExpireDate(currentDateTime.plusHours(24));
        userProfileRepository.save(userProfile);

        String[] receivers = {userProfile.getEmail()};
        String[] cc = {};
        String[] bcc = {};
        Map<String, Object> map = new HashMap<>();
        map.put("email", userProfile.getEmail());
        map.put("account", userProfile.getAccount());
        map.put("token", token);
        mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 重設密碼通知", "password-forgot", map);
        return userProfile.getAccount();
    }

    public String passwordForgot(String token, PasswordForgotRequestModel request) throws EmptyException, UserException {
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        if (StringUtils.isBlank(token) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordCheck)) {
            throw new EmptyException("密碼與密碼確認不得為空");
        }
        if (!StringUtils.equals(password, passwordCheck)) {
            throw new UserException("密碼與密碼確認不一致");
        }
        UserProfile userProfile = userProfileRepository.findByRefreshHash(token);
        if (Objects.isNull(userProfile)) {
            throw new UserException("token錯誤，請重新確認");
        }
        if (LocalDateTime.now().isAfter(userProfile.getPwdForgotTokenExpireDate())) {
            throw new UserException("token已過期，請重新申請");
        }

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePwd = bcryptPasswordEncoder.encode(password);
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

        userProfile.setPassword(encodePwd);
        userProfile.setPwdChangedDate(currentDateTime);
        userProfile.setPwdExpireDate(currentDateTime.plusMonths(3));
        userProfile.setUpdatedUser(userProfile.getUserId());
        userProfile.setUpdatedDate(currentDateTime);
        userProfile.setPwdForgotToken(null);
        userProfile.setPwdForgotTokenExpireDate(null);
        userProfileRepository.save(userProfile);
        return userProfile.getAccount();
    }
}
