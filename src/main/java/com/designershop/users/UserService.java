package com.designershop.users;

import com.designershop.admin.users.AdminUserService;
import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.entities.UserProfile;
import com.designershop.entities.UserRole;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.ReadUserResponseModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final HttpSession session;
    private final AdminUserService adminUserService;
    private final UserProfileRepository userProfileRepository;

    public String createUser(CreateUserRequestModel request) throws EmptyException, UserException, MessagingException {
        AdminCreateUserRequestModel adminCreateUserRequestModel = new AdminCreateUserRequestModel();
        BeanUtils.copyProperties(request, adminCreateUserRequestModel);
        adminCreateUserRequestModel.setUserType("B1");
        return adminUserService.createUser(adminCreateUserRequestModel);
    }

    public ReadUserResponseModel readUser(String userId) throws UserException {
        UserProfile userProfile = validateUserPermission(userId);
        ReadUserResponseModel response = new ReadUserResponseModel();
        BeanUtils.copyProperties(userProfile, response);
        if (Objects.nonNull(userProfile.getBirthday())) {
            response.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }
        response.setRegisterDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getRegisterDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        if (Objects.nonNull(userProfile.getPwdChangedDate())) {
            response.setPwdChangedDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdChangedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }
        response.setPwdExpireDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        return response;
    }

    public String updateUser(String userId, UpdateUserRequestModel request) throws EmptyException, UserException {
        UserProfile userProfile = validateUserPermission(userId);
        AdminUpdateUserRequestModel adminUpdateUserRequestModel = new AdminUpdateUserRequestModel();
        BeanUtils.copyProperties(request, adminUpdateUserRequestModel);
        for (UserRole userRole : userProfile.getUserRoles()) {
            switch (userRole.getCategory()) {
                case "buyer":
                    adminUpdateUserRequestModel.setUserType(userRole.getRoleId());
                    break;
                case "seller":
                    adminUpdateUserRequestModel.setSellerType(userRole.getRoleId());
                    break;
                case "designer":
                    adminUpdateUserRequestModel.setDesignerType(userRole.getRoleId());
                    break;
                case "admin":
                    adminUpdateUserRequestModel.setAdminType(userRole.getRoleId());
                    break;
                default:
                    break;
            }
        }

        adminUpdateUserRequestModel.setIsDeleted("N");
        return adminUserService.updateUser(userId, adminUpdateUserRequestModel);
    }

    public String updatePassword(String userId, UpdatePasswordRequestModel request) throws EmptyException, UserException, MessagingException {
        validateUserPermission(userId);
        AdminUpdatePasswordRequestModel adminUpdatePasswordRequestModel = new AdminUpdatePasswordRequestModel();
        BeanUtils.copyProperties(request, adminUpdatePasswordRequestModel);
        return adminUserService.updatePassword(userId, adminUpdatePasswordRequestModel);
    }

    public String deleteUser(String userId) throws UserException, ProductException, MessagingException {
        validateUserPermission(userId);
        return adminUserService.deleteUser(userId);
    }

    public UserProfile validateUserPermission(String userId) throws UserException {
        UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(sessionUserProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (Objects.isNull(userProfile) || !sessionUserProfile.equals(userProfile) || !StringUtils.equals(sessionUserProfile.getPassword(), userProfile.getPassword())) {
            throw new UserException("此帳戶不存在，請重新確認");
        }

        return userProfile;
    }
}
