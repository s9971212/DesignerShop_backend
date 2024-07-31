package com.designershop.users;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.models.PasswordForgotRequestModel;
import com.designershop.utils.DateTimeFormatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordForgotService {

	private final UserProfileRepository userProfileRepository;

	public String passwordForgot(String token, PasswordForgotRequestModel request) throws UserException {
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

		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodePwd = bcryptPasswordEncoder.encode(password);
		LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

		userProfile.setPassword(encodePwd);
		userProfile.setPwdChangedDate(Timestamp.valueOf(currentDateTime));
		userProfile.setPwdExpireDate(Timestamp.valueOf(currentDateTime.plusMonths(3)));
		userProfile.setModifyUser(userProfile.getUserId());
		userProfile.setModifyDate(Timestamp.valueOf(currentDateTime));

		userProfileRepository.save(userProfile);

		return userProfile.getAccount();
	}
}
