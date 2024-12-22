package com.designershop.coupons;

import com.designershop.admin.coupons.AdminCouponIssuanceService;
import com.designershop.admin.coupons.models.AdminCreateCouponIssuanceRequestModel;
import com.designershop.coupons.models.ReadCouponIssuanceResponseModel;
import com.designershop.entities.CouponIssuance;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.CouponIssuanceRepository;
import com.designershop.repositories.CouponRepository;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponIssuanceService {

    private final HttpSession session;
    private final AdminCouponIssuanceService adminCouponIssuanceService;
    private final CouponIssuanceRepository couponIssuanceRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createCouponIssuance(String couponId) throws  CouponException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        AdminCreateCouponIssuanceRequestModel adminCreateCouponIssuanceRequestModel = new AdminCreateCouponIssuanceRequestModel();
        List<String> userIds = new ArrayList<>();
        userIds.add(userProfile.getUserId());
        adminCreateCouponIssuanceRequestModel.setUserIds(userIds);

        return adminCouponIssuanceService.createCouponIssuance(couponId,adminCreateCouponIssuanceRequestModel);
    }

    public List<ReadCouponIssuanceResponseModel> readAllCouponIssuance(String couponId) throws CouponException{
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        List<ReadCouponIssuanceResponseModel> response = new ArrayList<>();

        List<CouponIssuance> couponIssuanceList = couponIssuanceRepository.findByUserIdAndCouponId(userProfile.getUserId(),Integer.parseInt(couponId));
        for (CouponIssuance couponIssuance : couponIssuanceList) {
            ReadCouponIssuanceResponseModel readCouponIssuanceResponseModel = new ReadCouponIssuanceResponseModel();
            BeanUtils.copyProperties(couponIssuance, readCouponIssuanceResponseModel);
            readCouponIssuanceResponseModel.setIssuanceId(Integer.toString(couponIssuance.getIssuanceId()));
            readCouponIssuanceResponseModel.setIsUsed(couponIssuance.isUsed() ? "Y" : "N");
            if (Objects.nonNull(couponIssuance.getUsedDate())) {
                readCouponIssuanceResponseModel.setUsedDate(DateTimeFormatUtil.localDateTimeFormat(couponIssuance.getUsedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            }

            response.add(readCouponIssuanceResponseModel);
        }

        return response;
    }

    public ReadCouponIssuanceResponseModel readCouponIssuance(String issuanceId) throws CouponException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        CouponIssuance couponIssuance = couponIssuanceRepository.findByIssuanceIdAndUserId(Integer.parseInt(issuanceId),userProfile.getUserId());
        if (Objects.isNull(couponIssuance)) {
            throw new CouponException("此優惠券發放記錄不存在，請重新確認");
        }

        ReadCouponIssuanceResponseModel response = new ReadCouponIssuanceResponseModel();
        BeanUtils.copyProperties(couponIssuance, response);
        response.setIssuanceId(issuanceId);
        response.setIsUsed(couponIssuance.isUsed() ? "Y" : "N");
        if (Objects.nonNull(couponIssuance.getUsedDate())) {
            response.setUsedDate(DateTimeFormatUtil.localDateTimeFormat(couponIssuance.getUsedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        }

        return response;
    }
}
