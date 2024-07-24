package com.designershop.users;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.designershop.admin.users.AdminUsersService;
import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;
import com.designershop.utils.DateTimeFormatUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final AdminUsersService adminUsersService;
	private final UserProfileRepository userProfileRepository;

	public String createUser(CreateUserRequestModel request) throws UserException {
		AdminCreateUserRequestModel adminCreateUserRequestModel = new AdminCreateUserRequestModel();
		BeanUtils.copyProperties(request, adminCreateUserRequestModel);
		adminCreateUserRequestModel.setUserType("B1");
		return adminUsersService.createUser(adminCreateUserRequestModel);
	}

	public UpdateUserRequestModel readUser(String userId) throws UserException {

		// TODO 之後改為從session取得account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		if (Objects.isNull(userProfile)) {
			throw new UserException("此帳戶不存在，請重新確認");
		}

		UpdateUserRequestModel response = new UpdateUserRequestModel();
		BeanUtils.copyProperties(userProfile, response);
		response.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday()));

		return response;
	}

	public String updateUser(String userId, UpdateUserRequestModel request) throws UserException {
		AdminUpdateUserRequestModel adminUpdateUserRequestModel = new AdminUpdateUserRequestModel();
		BeanUtils.copyProperties(request, adminUpdateUserRequestModel);

		// TODO 之後改為從session取得修改前的account(這裡暫時先只用userId查詢)
		UserProfile userProfile = userProfileRepository.findByUserId(userId);
		adminUpdateUserRequestModel.setUserType(userProfile.getUserType());
		adminUpdateUserRequestModel.setSellerType(userProfile.getSellerType());
		adminUpdateUserRequestModel.setDesignerType(userProfile.getDesignerType());
		adminUpdateUserRequestModel.setAdminType(userProfile.getAdminType());

		return adminUsersService.updateUser(userId, adminUpdateUserRequestModel);
	}

	public String updatePassword(String userId, UpdatePasswordRequestModel request) throws UserException {
		AdminUpdatePasswordRequestModel adminUpdatePasswordRequestModel = new AdminUpdatePasswordRequestModel();
		BeanUtils.copyProperties(request, adminUpdatePasswordRequestModel);
		return adminUsersService.updatePassword(userId, adminUpdatePasswordRequestModel);
	}

	public String deleteUser(String userId) throws UserException {
		return adminUsersService.deleteUser(userId);
	}
}
