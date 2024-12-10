package com.designershop.admin.coupons;

import com.designershop.admin.coupons.models.AdminCreateCouponIssuanceRequestModel;
import com.designershop.admin.coupons.models.AdminCreateCouponRequestModel;
import com.designershop.admin.coupons.models.AdminReadCouponResponseModel;
import com.designershop.admin.coupons.models.AdminUpdateCouponRequestModel;
import com.designershop.entities.*;
import com.designershop.enums.DiscountTypeEnum;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.EmptyException;
import com.designershop.repositories.*;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCouponIssuancesService {

    private final HttpSession session;
    private final UserProfileRepository userProfileRepository;
    private final CouponRepository couponRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createCouponIssuance(String couponId, AdminCreateCouponIssuanceRequestModel request) throws EmptyException, CouponException {
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

            CouponIssuance couponIssuanceCreate = new CouponIssuance();
            couponIssuanceCreate.setUserId(userProfile.getUserId());
            couponIssuanceCreate.setCoupon(coupon);
            couponIssuanceRepository.save(couponIssuanceCreate);
        }

        return coupon.getCode();
    }
}
