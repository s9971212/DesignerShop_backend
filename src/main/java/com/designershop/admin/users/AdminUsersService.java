package com.designershop.admin.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminReadUserResponseModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.entities.UserProfile;
import com.designershop.entities.UserRole;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.mail.MailService;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.repositories.UserRoleRepository;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.FormatUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUsersService {

	private final HttpSession session;
	private final MailService mailService;
	private final UserProfileRepository userProfileRepository;
	private final UserRoleRepository userRoleRepository;

	public String createUser(AdminCreateUserRequestModel request)
			throws EmptyException, UserException, MessagingException {
		String userType = request.getUserType();
		String sellerType = request.getSellerType();
		String designerType = request.getDesignerType();
		String adminType = request.getAdminType();
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
		String userImage = request.getUserImage();
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
		String userId = FormatUtil.userIdGenerate(userProfile);
		String encodePwd = new BCryptPasswordEncoder().encode(password);
		LocalDateTime birthday = null;
		if (StringUtils.isNotBlank(birthdayString)) {
			birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString);
		}
		LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
		String modifyUser = userId;
		if (Objects.nonNull(sessionUserProfile)) {
			modifyUser = sessionUserProfile.getUserId();
		}

		Set<String> roleIds = new HashSet<>();
		roleIds.add(userType);
		roleIds.add(sellerType);
		roleIds.add(designerType);
		roleIds.add(adminType);
		Set<UserRole> userRoles = new HashSet<>();
		for (String roleId : roleIds) {
			UserRole userRole = userRoleRepository.findByRoleId(roleId);
			if (Objects.nonNull(userRole)) {
				userRoles.add(userRole);
			}
		}

		UserProfile userProfileCreate = new UserProfile();
		userProfileCreate.setUserId(userId);
		userProfileCreate.setAccount(account);
		userProfileCreate.setPassword(encodePwd);
		userProfileCreate.setEmail(email);
		userProfileCreate.setPhoneNo(phoneNo);
		userProfileCreate.setUserName(userName);
		userProfileCreate.setGender(gender);
		userProfileCreate.setBirthday(birthday);
		userProfileCreate.setIdCardNo(idCardNo);
		userProfileCreate.setHomeNo(homeNo);
		userProfileCreate.setUserImage(userImage);
		userProfileCreate.setRegisterDate(currentDateTime);
		userProfileCreate.setPwdExpireDate(currentDateTime.plusMonths(3));
		userProfileCreate.setModifyUser(modifyUser);
		userProfileCreate.setModifyDate(currentDateTime);
		userProfileCreate.setUserRoles(userRoles);

		userProfileRepository.save(userProfileCreate);

		String[] receivers = { userProfileCreate.getEmail() };
		String[] cc = {};
		String[] bcc = {};
		Map<String, Object> map = new HashMap<>();
		map.put("email", userProfileCreate.getEmail());
		map.put("account", userProfileCreate.getAccount());
		map.put("pwdExpireDate", DateTimeFormatUtil.localDateTimeFormat(userProfileCreate.getPwdExpireDate()));
		mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 註冊成功通知", "register", map);

		return account;
	}

	public List<AdminReadUserResponseModel> readAllUser() {
		List<AdminReadUserResponseModel> response = new ArrayList<>();

		List<UserProfile> userProfileList = userProfileRepository.findAll();
		for (UserProfile userProfile : userProfileList) {
			AdminReadUserResponseModel adminReadUserResponseModel = new AdminReadUserResponseModel();
			BeanUtils.copyProperties(userProfile, adminReadUserResponseModel);

			for (UserRole userRole : userProfile.getUserRoles()) {
				switch (userRole.getRoleCategory()) {
				case "buyer":
					adminReadUserResponseModel.setUserType(userRole.getRoleId());
					break;
				case "seller":
					adminReadUserResponseModel.setSellerType(userRole.getRoleId());
					break;
				case "designer":
					adminReadUserResponseModel.setDesignerType(userRole.getRoleId());
					break;
				case "admin":
					adminReadUserResponseModel.setAdminType(userRole.getRoleId());
					break;
				default:
					break;
				}
			}

			if (Objects.nonNull(userProfile.getBirthday())) {
				adminReadUserResponseModel
						.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday()));
			}
			adminReadUserResponseModel
					.setRegisterDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getRegisterDate()));
			if (Objects.nonNull(userProfile.getPwdChangedDate())) {
				adminReadUserResponseModel
						.setPwdChangedDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdChangedDate()));
			}
			adminReadUserResponseModel
					.setPwdExpireDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate()));
			response.add(adminReadUserResponseModel);
		}

		return response;
	}

	public AdminReadUserResponseModel readUser(String userId) throws UserException {
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		AdminReadUserResponseModel response = new AdminReadUserResponseModel();
		BeanUtils.copyProperties(userProfile, response);

		for (UserRole userRole : userProfile.getUserRoles()) {
			switch (userRole.getRoleCategory()) {
			case "buyer":
				response.setUserType(userRole.getRoleId());
				break;
			case "seller":
				response.setSellerType(userRole.getRoleId());
				break;
			case "designer":
				response.setDesignerType(userRole.getRoleId());
				break;
			case "admin":
				response.setAdminType(userRole.getRoleId());
				break;
			default:
				break;
			}
		}

		if (Objects.nonNull(userProfile.getBirthday())) {
			response.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday()));
		}
		response.setRegisterDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getRegisterDate()));
		if (Objects.nonNull(userProfile.getPwdChangedDate())) {
			response.setPwdChangedDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdChangedDate()));
		}
		response.setPwdExpireDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate()));

		return response;
	}

	public String updateUser(String userId, AdminUpdateUserRequestModel request) throws EmptyException, UserException {
		String userType = request.getUserType();
		String sellerType = request.getSellerType();
		String designerType = request.getDesignerType();
		String adminType = request.getAdminType();
		String account = request.getAccount();
		String email = request.getEmail();
		String phoneNo = request.getPhoneNo();
		String userName = request.getUserName();
		String gender = request.getGender();
		String birthdayString = request.getBirthday();
		String idCardNo = request.getIdCardNo();
		String homeNo = request.getHomeNo();
		String userImage = request.getUserImage();
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
			if (!StringUtils.equals(userId, userProfile.getUserId())) {
				if (StringUtils.equals(account, userProfile.getAccount())) {
					throw new UserException("此帳號已被註冊，請使用別的帳號");
				} else if (StringUtils.equals(email, userProfile.getEmail())) {
					throw new UserException("此Email已被註冊，請使用別的Email");
				} else if (StringUtils.equals(phoneNo, userProfile.getPhoneNo())) {
					throw new UserException("此手機已被註冊，請使用別的手機");
				}
			}
		}

		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		LocalDateTime birthday = userProfile.getBirthday();
		if (StringUtils.isNotBlank(birthdayString)) {
			birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString);
		}

		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");

		Set<String> roleIds = new HashSet<>();
		roleIds.add(userType);
		roleIds.add(sellerType);
		roleIds.add(designerType);
		roleIds.add(adminType);
		Set<UserRole> userRoles = new HashSet<>();
		for (String roleId : roleIds) {
			UserRole userRole = userRoleRepository.findByRoleId(roleId);
			if (Objects.nonNull(userRole)) {
				userRoles.add(userRole);
			}
		}

		userProfile.setAccount(account);
		userProfile.setEmail(email);
		userProfile.setPhoneNo(phoneNo);
		userProfile.setUserName(userName);
		userProfile.setGender(gender);
		userProfile.setBirthday(birthday);
		userProfile.setIdCardNo(idCardNo);
		userProfile.setHomeNo(homeNo);
		userProfile.setUserImage(userImage);
		userProfile.setModifyUser(sessionUserProfile.getUserId());
		userProfile.setModifyDate(DateTimeFormatUtil.currentDateTime());
		userProfile.setUserRoles(userRoles);

		userProfileRepository.save(userProfile);

		return account;
	}

	public String updatePassword(String userId, AdminUpdatePasswordRequestModel request)
			throws EmptyException, UserException, MessagingException {
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
		LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();
		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");

		userProfile.setPassword(encodePwd);
		userProfile.setPwdChangedDate(currentDateTime);
		userProfile.setPwdExpireDate(currentDateTime.plusMonths(3));
		userProfile.setModifyUser(sessionUserProfile.getUserId());
		userProfile.setModifyDate(currentDateTime);

		userProfileRepository.save(userProfile);

		String[] receivers = { userProfile.getEmail() };
		String[] cc = {};
		String[] bcc = {};
		Map<String, Object> map = new HashMap<>();
		map.put("email", userProfile.getEmail());
		map.put("account", userProfile.getAccount());
		map.put("pwdExpireDate", DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate()));
		mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 密碼變更通知", "password-changed", map);

		return userProfile.getAccount();
	}

	public String deleteUser(String userId) throws UserException, MessagingException {
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		userProfileRepository.delete(userProfile);

		String[] receivers = { userProfile.getEmail() };
		String[] cc = {};
		String[] bcc = {};
		Map<String, Object> map = new HashMap<>();
		map.put("email", userProfile.getEmail());
		map.put("account", userProfile.getAccount());
		mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 帳戶刪除通知", "account-deleted", map);

		return userProfile.getAccount();
	}
}
