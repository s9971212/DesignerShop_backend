package com.designershop.admin.coupons;

import com.designershop.admin.coupons.models.*;
import com.designershop.entities.*;
import com.designershop.exceptions.CouponException;
import com.designershop.repositories.*;
import com.designershop.utils.DateTimeFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminCouponIssuanceService {

    private final UserProfileRepository userProfileRepository;
    private final CouponRepository couponRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createCouponIssuance(String couponId, AdminCreateCouponIssuanceRequestModel request) throws CouponException {
        List<String> userIds = request.getUserIds();

        Coupon coupon = couponRepository.findByCouponId(Integer.parseInt(couponId));
        if (Objects.isNull(coupon)) {
            throw new CouponException("此優惠券不存在，請重新確認");
        }

        List<UserProfile> userProfiles = userProfileRepository.findByUserIds(userIds);
        Map<String, UserProfile> userProfileMap = userProfiles.stream().collect(Collectors.toMap(UserProfile::getUserId, userProfile -> userProfile));
        for (String userId : userIds) {
            UserProfile userProfile = userProfileMap.get(userId);
            if (Objects.isNull(userProfile)) {
                throw new CouponException("某些帳戶不存在，請重新確認");
            }

            if (userProfile.isDeleted()) {
                throw new CouponException("某些帳戶已被刪除，請重新確認");
            }

            List<CouponIssuance> couponIssuances = couponIssuanceRepository.findByUserIdAndCouponId(userId, Integer.parseInt(couponId));
            if (!couponIssuances.isEmpty()) {
                throw new CouponException("某些帳戶已領取過優惠券，請重新確認");
            }

            for (int i = 0; i < coupon.getIssuanceLimit(); i++) {
                CouponIssuance couponIssuanceCreate = new CouponIssuance();
                couponIssuanceCreate.setUserId(userId);
                couponIssuanceCreate.setCoupon(coupon);
                couponIssuanceRepository.save(couponIssuanceCreate);
            }
        }

        return coupon.getCode();
    }

    public List<AdminReadCouponIssuanceResponseModel> readAllCouponIssuance(String couponId) {
        List<AdminReadCouponIssuanceResponseModel> response = new ArrayList<>();

        List<CouponIssuance> couponIssuanceList = couponIssuanceRepository.findAllByCouponId(Integer.parseInt(couponId));
        for (CouponIssuance couponIssuance : couponIssuanceList) {
            AdminReadCouponIssuanceResponseModel adminReadCouponIssuanceResponseModel = new AdminReadCouponIssuanceResponseModel();
            BeanUtils.copyProperties(couponIssuance, adminReadCouponIssuanceResponseModel);
            adminReadCouponIssuanceResponseModel.setIssuanceId(Integer.toString(couponIssuance.getIssuanceId()));
            adminReadCouponIssuanceResponseModel.setIsUsed(couponIssuance.isUsed() ? "Y" : "N");
            if (Objects.nonNull(couponIssuance.getUsedDate())) {
                adminReadCouponIssuanceResponseModel.setUsedDate(DateTimeFormatUtil.localDateTimeFormat(couponIssuance.getUsedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }
            adminReadCouponIssuanceResponseModel.setUserId(couponIssuance.getUserId());

            response.add(adminReadCouponIssuanceResponseModel);
        }

        return response;
    }

    public AdminReadCouponIssuanceResponseModel readCouponIssuance(String issuanceId) throws CouponException {
        CouponIssuance couponIssuance = couponIssuanceRepository.findByIssuanceId(Integer.parseInt(issuanceId));
        if (Objects.isNull(couponIssuance)) {
            throw new CouponException("此優惠券發放記錄不存在，請重新確認");
        }

        AdminReadCouponIssuanceResponseModel response = new AdminReadCouponIssuanceResponseModel();
        BeanUtils.copyProperties(couponIssuance, response);
        response.setIssuanceId(issuanceId);
        response.setIsUsed(couponIssuance.isUsed() ? "Y" : "N");
        if (Objects.nonNull(couponIssuance.getUsedDate())) {
            response.setUsedDate(DateTimeFormatUtil.localDateTimeFormat(couponIssuance.getUsedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }
        response.setUserId(couponIssuance.getUserId());

        return response;
    }
}
