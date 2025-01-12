package com.designershop.admin.users;

import com.designershop.admin.products.AdminProductService;
import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminReadUserResponseModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.entities.Product;
import com.designershop.entities.UserProfile;
import com.designershop.entities.UserRole;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.mail.MailService;
import com.designershop.repositories.ProductRepository;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.users.UserRoleService;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.FormatUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final HttpSession session;
    private final UserRoleService userRoleService;
    private final MailService mailService;
    private final AdminProductService adminProductService;
    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createUser(AdminCreateUserRequestModel request) throws EmptyException, UserException, MessagingException {
        String userType = request.getUserType();
        String sellerType = request.getSellerType();
        String designerType = request.getDesignerType();
        String adminType = request.getAdminType();
        String account = request.getAccount();
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();
        String email = request.getEmail();
        String phoneNo = request.getPhoneNo();
        String name = request.getName();
        String gender = request.getGender();
        String birthdayString = request.getBirthday();
        String idCardNo = request.getIdCardNo();
        String homeNo = request.getHomeNo();
        String userImage = request.getImage();
        String termsCheckBox = request.getTermsCheckBox();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordCheck) || StringUtils.isBlank(email)
                || StringUtils.isBlank(phoneNo) || StringUtils.isBlank(termsCheckBox)) {
            throw new EmptyException("帳號、密碼、密碼確認、Email、手機與條款確認不得為空");
        }
        if (!StringUtils.equals(password, passwordCheck)) {
            throw new UserException("密碼與密碼確認不一致");
        }
        if (!phoneNo.matches("^0?9\\d{8}$")) {
            throw new UserException("手機格式錯誤");
        }
        List<UserProfile> userProfileList = userProfileRepository.findByAccountOrEmailOrPhoneNo(account, email, phoneNo);
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
            birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString, DateTimeFormatUtil.FULL_DATE_DASH_TIME);
        }
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();
        UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
        String updatedUser = userId;
        if (Objects.nonNull(sessionUserProfile)) {
            updatedUser = sessionUserProfile.getUserId();
        }
        Set<UserRole> userRoleSet = userRoleService.readUserRole(userType, sellerType, designerType, adminType);

        UserProfile userProfileCreate = new UserProfile();
        userProfileCreate.setUserId(userId);
        userProfileCreate.setAccount(account);
        userProfileCreate.setPassword(encodePwd);
        userProfileCreate.setEmail(email);
        userProfileCreate.setPhoneNo(phoneNo);
        userProfileCreate.setName(name);
        userProfileCreate.setGender(gender);
        userProfileCreate.setBirthday(birthday);
        userProfileCreate.setIdCardNo(idCardNo);
        userProfileCreate.setHomeNo(homeNo);
        userProfileCreate.setImage(userImage);
        userProfileCreate.setRegisterDate(currentDateTime);
        userProfileCreate.setPwdExpireDate(currentDateTime.plusMonths(3));
        userProfileCreate.setUpdatedUser(updatedUser);
        userProfileCreate.setUpdatedDate(currentDateTime);
        userProfileCreate.setUserRoles(userRoleSet);
        userProfileRepository.save(userProfileCreate);

        String[] receivers = {userProfileCreate.getEmail()};
        String[] cc = {};
        String[] bcc = {};
        Map<String, Object> map = new HashMap<>();
        map.put("email", userProfileCreate.getEmail());
        map.put("account", userProfileCreate.getAccount());
        map.put("pwdExpireDate", DateTimeFormatUtil.localDateTimeFormat(userProfileCreate.getPwdExpireDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
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
                switch (userRole.getCategory()) {
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
                adminReadUserResponseModel.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }
            adminReadUserResponseModel.setRegisterDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getRegisterDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            if (Objects.nonNull(userProfile.getPwdChangedDate())) {
                adminReadUserResponseModel.setPwdChangedDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdChangedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }
            adminReadUserResponseModel.setPwdExpireDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            adminReadUserResponseModel.setIsDeleted(userProfile.isDeleted() ? "Y" : "N");
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
            switch (userRole.getCategory()) {
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
            response.setBirthday(DateTimeFormatUtil.localDateTimeFormat(userProfile.getBirthday(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }
        response.setRegisterDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getRegisterDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        if (Objects.nonNull(userProfile.getPwdChangedDate())) {
            response.setPwdChangedDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdChangedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }
        response.setPwdExpireDate(DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        response.setIsDeleted(userProfile.isDeleted() ? "Y" : "N");
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateUser(String userId, AdminUpdateUserRequestModel request) throws EmptyException, UserException {
        String userType = request.getUserType();
        String sellerType = request.getSellerType();
        String designerType = request.getDesignerType();
        String adminType = request.getAdminType();
        String account = request.getAccount();
        String email = request.getEmail();
        String phoneNo = request.getPhoneNo();
        String name = request.getName();
        String gender = request.getGender();
        String birthdayString = request.getBirthday();
        String idCardNo = request.getIdCardNo();
        String homeNo = request.getHomeNo();
        String image = request.getImage();
        String isDeletedString = request.getIsDeleted();
        String termsCheckBox = request.getTermsCheckBox();

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(account) || StringUtils.isBlank(email) || StringUtils.isBlank(phoneNo)
                || StringUtils.isBlank(isDeletedString) || StringUtils.isBlank(termsCheckBox)) {
            throw new EmptyException("帳號、Email、手機與條款確認不得為空");
        }
        if (!phoneNo.matches("^0?9\\d{8}$")) {
            throw new UserException("手機格式錯誤");
        }
        List<UserProfile> userProfileList = userProfileRepository.findByAccountOrEmailOrPhoneNo(account, email, phoneNo);
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
            birthday = DateTimeFormatUtil.localDateTimeFormat(birthdayString, DateTimeFormatUtil.FULL_DATE_DASH_TIME);
        }
        UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
        boolean isDeleted = StringUtils.equals("Y", isDeletedString);
        Set<UserRole> userRoleSet = userRoleService.readUserRole(userType, sellerType, designerType, adminType);

        userProfile.setAccount(account);
        userProfile.setEmail(email);
        userProfile.setPhoneNo(phoneNo);
        userProfile.setName(name);
        userProfile.setGender(gender);
        userProfile.setBirthday(birthday);
        userProfile.setIdCardNo(idCardNo);
        userProfile.setHomeNo(homeNo);
        userProfile.setImage(image);
        userProfile.setUpdatedUser(sessionUserProfile.getUserId());
        userProfile.setUpdatedDate(DateTimeFormatUtil.currentDateTime());
        userProfile.setDeleted(isDeleted);
        userProfile.setUserRoles(userRoleSet);
        userProfileRepository.save(userProfile);
        return account;
    }

    public String updatePassword(String userId, AdminUpdatePasswordRequestModel request) throws EmptyException, UserException, MessagingException {
        String oldPassword = request.getOldPassword();
        String password = request.getPassword();
        String passwordCheck = request.getPasswordCheck();

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password) || StringUtils.isBlank(passwordCheck)) {
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
        userProfile.setUpdatedUser(sessionUserProfile.getUserId());
        userProfile.setUpdatedDate(currentDateTime);
        userProfileRepository.save(userProfile);

        String[] receivers = {userProfile.getEmail()};
        String[] cc = {};
        String[] bcc = {};
        Map<String, Object> map = new HashMap<>();
        map.put("email", userProfile.getEmail());
        map.put("account", userProfile.getAccount());
        map.put("pwdExpireDate", DateTimeFormatUtil.localDateTimeFormat(userProfile.getPwdExpireDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 密碼變更通知", "password-changed", map);
        return userProfile.getAccount();
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteUser(String userId) throws UserException, ProductException, MessagingException {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶不存在，請重新確認");
        }

        UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");

        userProfile.setUpdatedUser(sessionUserProfile.getUserId());
        userProfile.setUpdatedDate(DateTimeFormatUtil.currentDateTime());
        userProfile.setDeleted(true);
        userProfileRepository.save(userProfile);

        List<Product> productList = productRepository.findAllByUserId(userId);
        for (Product product : productList) {
            adminProductService.deleteProduct(Integer.toString(product.getProductId()));
        }

        String[] receivers = {userProfile.getEmail()};
        String[] cc = {};
        String[] bcc = {};
        Map<String, Object> map = new HashMap<>();
        map.put("email", userProfile.getEmail());
        map.put("account", userProfile.getAccount());
        mailService.sendEmailWithTemplate(receivers, cc, bcc, "DesignerShop 帳戶刪除通知", "account-deleted", map);
        return userProfile.getAccount();
    }
}
