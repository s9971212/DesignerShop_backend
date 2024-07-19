package com.designershop.users;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.FormatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UserProfileRepository userProfileRepository;

	public String createUser(CreateUserRequestModel request) throws UserException {
		String account = request.getAccount();
		String password = request.getPassword();
		String passwordCheck = request.getPasswordCheck();
		String email = request.getEmail();
		String phoneNo = request.getPhoneNo();
		String userName = request.getUserName();
		String gender = request.getGender();
		String birthdayString = request.getBirthday();
		String idCardNo = request.getIdCardNo();
		String homeNo = request.getHomeNo();
		String userPhoto = request.getUserPhoto();
		String termsCheckBox = request.getTermsCheckBox();

		if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordCheck)
				|| StringUtils.isBlank(email) || StringUtils.isBlank(phoneNo) || StringUtils.isBlank(termsCheckBox)) {
			throw new EmptyException("帳號、密碼、密碼確認、Email、手機與條款確認不得為空");
		}

		if (!StringUtils.equals(password, passwordCheck)) {
			throw new UserException("密碼與密碼確認不一致");
		}

		if (!phoneNo.matches("^09\\d{8}$")) {
			throw new UserException("手機格式錯誤");
		}

		List<UserProfile> userProfileList = userProfileRepository.findByAccountOrEmailOrPhoneNo(account, email,
				phoneNo);
		for (UserProfile userProfile : userProfileList) {
			if (StringUtils.equals(account, userProfile.getAccount())) {
				throw new UserException("此帳號已被註冊，請使用別的帳號");
			} else if (StringUtils.equals(email, userProfile.getEmail())) {
				throw new UserException("此Email已被註冊，請使用別的Email");
			} else if (StringUtils.equals(phoneNo, userProfile.getPhoneNo())) {
				throw new UserException("此手機已被註冊，請使用別的手機");
			}
		}

		UserProfile userProfile = userProfileRepository.findMaxUserId();
		String userId = FormatUtil.userIdGenerate(userProfile.getUserId().substring(5, 10));
		String encodePwd = new BCryptPasswordEncoder().encode(password);
		Timestamp birthday = null;
		if (StringUtils.isNotBlank(birthdayString)) {
			birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString);
		}

		UserProfile userProfileCreate = new UserProfile();
		userProfileCreate.setUserId(userId);
		userProfileCreate.setUserType("B1");
		userProfileCreate.setAccount(account);
		userProfileCreate.setPassword(encodePwd);
		userProfileCreate.setEmail(email);
		userProfileCreate.setPhoneNo(phoneNo);
		userProfileCreate.setUserName(userName);
		userProfileCreate.setGender(gender);
		userProfileCreate.setBirthday(birthday);
		userProfileCreate.setIdCardNo(idCardNo);
		userProfileCreate.setHomeNo(homeNo);
		userProfileCreate.setUserPhoto(userPhoto);
		userProfileCreate.setRegisterDate(DateTimeFormatUtil.currentDateTimeFormat());
		userProfileCreate.setPwdExpireDate(DateTimeFormatUtil.pwdExpireDateTimeFormat());
		userProfileCreate.setModifyUser(userId);
		userProfileCreate.setModifyDate(DateTimeFormatUtil.currentDateTimeFormat());

		userProfileRepository.save(userProfileCreate);

		return account;
	}

	public UpdateUserRequestModel readUser(String userId) throws UserException {

		// TODO 之後改為從session取得修改前的account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		UpdateUserRequestModel response = new UpdateUserRequestModel();
		response.setUserId(userProfile.getUserId());
		response.setAccount(userProfile.getAccount());
		response.setEmail(userProfile.getEmail());
		response.setPhoneNo(userProfile.getPhoneNo());
		response.setUserName(userProfile.getUserName());
		response.setGender(userProfile.getGender());
		response.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday()));
		response.setIdCardNo(userProfile.getIdCardNo());
		response.setHomeNo(userProfile.getHomeNo());
		response.setUserPhoto(userProfile.getUserPhoto());

		return response;
	}

	public String updateUser(String userId, UpdateUserRequestModel request) throws UserException {
		String account = request.getAccount();
		String email = request.getEmail();
		String phoneNo = request.getPhoneNo();
		String userName = request.getUserName();
		String gender = request.getGender();
		String birthdayString = request.getBirthday();
		String idCardNo = request.getIdCardNo();
		String homeNo = request.getHomeNo();
		String userPhoto = request.getUserPhoto();
		String termsCheckBox = request.getTermsCheckBox();

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(account) || StringUtils.isBlank(email)
				|| StringUtils.isBlank(phoneNo) || StringUtils.isBlank(termsCheckBox)) {
			throw new EmptyException("帳號、Email、手機與條款確認不得為空");
		}

		if (!phoneNo.matches("^09\\d{8}$")) {
			throw new UserException("手機格式錯誤");
		}

		List<UserProfile> userProfileList = userProfileRepository.findByAccountOrEmailOrPhoneNo(account, email,
				phoneNo);
		for (UserProfile userProfile : userProfileList) {
			if (StringUtils.equals(account, userProfile.getAccount())) {
				throw new UserException("此帳號已被註冊，請使用別的帳號");
			} else if (StringUtils.equals(email, userProfile.getEmail())) {
				throw new UserException("此Email已被註冊，請使用別的Email");
			} else if (StringUtils.equals(phoneNo, userProfile.getPhoneNo())) {
				throw new UserException("此手機已被註冊，請使用別的手機");
			}
		}

		// TODO 之後改為從session取得修改前的account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		Timestamp birthday = userProfile.getBirthday();
		if (StringUtils.isNotBlank(birthdayString)) {
			birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString);
		}

		UserProfile userProfileUpdate = new UserProfile();
		userProfileUpdate.setUserId(userProfile.getUserId());
		userProfileUpdate.setUserType(userProfile.getUserType());
		userProfileUpdate.setAccount(account);
		userProfileUpdate.setPassword(userProfile.getPassword());
		userProfileUpdate.setEmail(email);
		userProfileUpdate.setPhoneNo(phoneNo);
		userProfileUpdate.setUserName(userName);
		userProfileUpdate.setGender(gender);
		userProfileUpdate.setBirthday(birthday);
		userProfileUpdate.setIdCardNo(idCardNo);
		userProfileUpdate.setHomeNo(homeNo);
		userProfileUpdate.setUserPhoto(userPhoto);
		userProfileUpdate.setRegisterDate(userProfile.getRegisterDate());
		userProfileUpdate.setPwdExpireDate(userProfile.getPwdExpireDate());
		userProfileUpdate.setModifyUser(userProfile.getUserId());
		userProfileUpdate.setModifyDate(DateTimeFormatUtil.currentDateTimeFormat());

		userProfileRepository.save(userProfileUpdate);

		return account;
	}

	public String updatePassword(String userId, UpdatePasswordRequestModel request) throws UserException {
		String oldPassword = request.getOldPassword();
		String password = request.getPassword();
		String passwordCheck = request.getPasswordCheck();

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)
				|| StringUtils.isBlank(passwordCheck)) {
			throw new EmptyException("舊密碼、密碼與密碼確認不得為空");
		}

		if (!StringUtils.equals(password, passwordCheck)) {
			throw new UserException("密碼與密碼確認不一致");
		}

		// TODO 之後改為從session取得account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		if (!bcryptPasswordEncoder.matches(oldPassword, userProfile.getPassword())) {
			throw new UserException("舊密碼錯誤，請重新確認");
		}
		if (bcryptPasswordEncoder.matches(password, userProfile.getPassword())) {
			throw new UserException("舊密碼與新密碼一樣，請重新確認");
		}

		String encodePwd = bcryptPasswordEncoder.encode(password);

		UserProfile userProfilePasswordUpdate = new UserProfile();
		userProfilePasswordUpdate.setUserId(userProfile.getUserId());
		userProfilePasswordUpdate.setUserType(userProfile.getUserType());
		userProfilePasswordUpdate.setAccount(userProfile.getAccount());
		userProfilePasswordUpdate.setPassword(encodePwd);
		userProfilePasswordUpdate.setEmail(userProfile.getEmail());
		userProfilePasswordUpdate.setPhoneNo(userProfile.getPhoneNo());
		userProfilePasswordUpdate.setUserName(userProfile.getUserName());
		userProfilePasswordUpdate.setGender(userProfile.getGender());
		userProfilePasswordUpdate.setBirthday(userProfile.getBirthday());
		userProfilePasswordUpdate.setIdCardNo(userProfile.getIdCardNo());
		userProfilePasswordUpdate.setHomeNo(userProfile.getHomeNo());
		userProfilePasswordUpdate.setUserPhoto(userProfile.getUserPhoto());
		userProfilePasswordUpdate.setRegisterDate(userProfile.getRegisterDate());
		userProfilePasswordUpdate.setPwdChangedDate(DateTimeFormatUtil.currentDateTimeFormat());
		userProfilePasswordUpdate.setPwdExpireDate(DateTimeFormatUtil.pwdExpireDateTimeFormat());
		userProfilePasswordUpdate.setModifyUser(userProfile.getUserId());
		userProfilePasswordUpdate.setModifyDate(DateTimeFormatUtil.currentDateTimeFormat());

		userProfileRepository.save(userProfilePasswordUpdate);

		// TODO 之後改為從session取得account
		return userProfile.getAccount();
	}

	public String deleteUser(String userId) throws UserException {

		// TODO 之後改為從session取得修改前的account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		userProfileRepository.delete(userProfile);

		// TODO 之後改為從session取得account
		return userProfile.getAccount();
	}
}
