package com.designershop.coupons;

import com.designershop.admin.coupons.AdminCouponService;
import com.designershop.admin.coupons.models.AdminCreateCouponRequestModel;
import com.designershop.admin.coupons.models.AdminUpdateCouponRequestModel;
import com.designershop.coupons.models.CreateCouponRequestModel;
import com.designershop.coupons.models.ReadCouponResponseModel;
import com.designershop.coupons.models.UpdateCouponRequestModel;
import com.designershop.entities.*;
import com.designershop.exceptions.CouponException;
import com.designershop.exceptions.EmptyException;
import com.designershop.repositories.*;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SellerCouponService {

    private final HttpSession session;
    private final AdminCouponService adminCouponService;
    private final CouponRepository couponRepository;
    private final CouponUserProfileRepository couponUserProfileRepository;
    private final CouponProductCategoryRepository couponProductCategoryRepository;
    private final CouponProductBrandRepository couponProductBrandRepository;
    private final CouponProductRepository couponProductRepository;

    public String createCoupon(CreateCouponRequestModel request) throws EmptyException, CouponException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        AdminCreateCouponRequestModel adminCreateCouponRequestModel = new AdminCreateCouponRequestModel();
        BeanUtils.copyProperties(request, adminCreateCouponRequestModel);
        List<String> userIds = new ArrayList<>();
        userIds.add(userProfile.getUserId());
        adminCreateCouponRequestModel.setUserIds(userIds);

        return adminCouponService.createCoupon(adminCreateCouponRequestModel);
    }

    public List<ReadCouponResponseModel> readAllCoupon() throws CouponException {
        List<ReadCouponResponseModel> response = new ArrayList<>();

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        List<CouponUserProfile> couponUserProfileList = couponUserProfileRepository.findAllByUserId(userProfile.getUserId());
        List<Integer> couponIds = couponUserProfileList.stream().map(couponUserProfile -> couponUserProfile.getId().getCouponId()).toList();
        List<Coupon> couponList = couponRepository.findByCouponIds(couponIds);
        for (Coupon coupon : couponList) {
            int couponId = coupon.getCouponId();

            ReadCouponResponseModel readCouponResponseModel = new ReadCouponResponseModel();
            BeanUtils.copyProperties(coupon, readCouponResponseModel);
            readCouponResponseModel.setCouponId(Integer.toString(couponId));
            readCouponResponseModel.setDiscountType(coupon.getDiscountType().name().toLowerCase());
            readCouponResponseModel.setDiscountValue(coupon.getDiscountValue().toString());
            if (Objects.nonNull(coupon.getMinimumOrderPrice())) {
                readCouponResponseModel.setMinimumOrderPrice(coupon.getMinimumOrderPrice().toString());
            }
            if (Objects.nonNull(coupon.getIssuanceLimit())) {
                readCouponResponseModel.setIssuanceLimit(Integer.toString(coupon.getIssuanceLimit()));
            }
            if (Objects.nonNull(coupon.getUsageLimit())) {
                readCouponResponseModel.setUsageLimit(Integer.toString(coupon.getUsageLimit()));
            }
            readCouponResponseModel.setStartDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getStartDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            readCouponResponseModel.setEndDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getEndDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            readCouponResponseModel.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            readCouponResponseModel.setIsActive(coupon.isActive() ? "Y" : "N");

            List<CouponProductCategory> couponProductCategoryList = couponProductCategoryRepository.findAllByCouponId(couponId);
            List<String> categoryIds = couponProductCategoryList.stream().map(couponProductCategory -> Integer.toString(couponProductCategory.getId().getCategoryId())).toList();
            readCouponResponseModel.setCategoryIds(categoryIds);
            List<CouponProductBrand> couponProductBrandList = couponProductBrandRepository.findAllByCouponId(couponId);
            List<String> brandIds = couponProductBrandList.stream().map(couponProductBrand -> Integer.toString(couponProductBrand.getId().getBrandId())).toList();
            readCouponResponseModel.setBrandIds(brandIds);
            List<CouponProduct> couponProductList = couponProductRepository.findAllByCouponId(couponId);
            List<String> productIds = couponProductList.stream().map(couponProduct -> Integer.toString(couponProduct.getId().getProductId())).toList();
            readCouponResponseModel.setProductIds(productIds);

            response.add(readCouponResponseModel);
        }

        return response;
    }

    public ReadCouponResponseModel readCoupon(String couponId) throws  CouponException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        CouponUserProfile couponUserProfile = couponUserProfileRepository.findByCouponIdAndUserId(Integer.parseInt(couponId), userProfile.getUserId());
        if (Objects.isNull(couponUserProfile)) {
            throw new CouponException("此優惠券不存在，請重新確認");
        }

        Coupon coupon = couponRepository.findByCouponId(Integer.parseInt(couponId));
        if (Objects.isNull(coupon)) {
            throw new CouponException("此優惠券不存在，請重新確認");
        }

        ReadCouponResponseModel response = new ReadCouponResponseModel();
        BeanUtils.copyProperties(coupon, response);
        response.setCouponId(couponId);
        response.setDiscountType(coupon.getDiscountType().name().toLowerCase());
        response.setDiscountValue(coupon.getDiscountValue().toString());
        if (Objects.nonNull(coupon.getMinimumOrderPrice())) {
            response.setMinimumOrderPrice(coupon.getMinimumOrderPrice().toString());
        }
        if (Objects.nonNull(coupon.getIssuanceLimit())) {
            response.setIssuanceLimit(Integer.toString(coupon.getIssuanceLimit()));
        }
        if (Objects.nonNull(coupon.getUsageLimit())) {
            response.setUsageLimit(Integer.toString(coupon.getUsageLimit()));
        }
        response.setStartDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getStartDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        response.setEndDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getEndDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        response.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        response.setIsActive(coupon.isActive() ? "Y" : "N");

        List<CouponProductCategory> couponProductCategoryList = couponProductCategoryRepository.findAllByCouponId(Integer.parseInt(couponId));
        List<String> categoryIds = couponProductCategoryList.stream().map(couponProductCategory -> Integer.toString(couponProductCategory.getId().getCategoryId())).toList();
        response.setCategoryIds(categoryIds);
        List<CouponProductBrand> couponProductBrandList = couponProductBrandRepository.findAllByCouponId(Integer.parseInt(couponId));
        List<String> brandIds = couponProductBrandList.stream().map(couponProductBrand -> Integer.toString(couponProductBrand.getId().getBrandId())).toList();
        response.setBrandIds(brandIds);
        List<CouponProduct> couponProductList = couponProductRepository.findAllByCouponId(Integer.parseInt(couponId));
        List<String> productIds = couponProductList.stream().map(couponProduct -> Integer.toString(couponProduct.getId().getProductId())).toList();
        response.setProductIds(productIds);

        return response;
    }

    public String updateCoupon(String couponId, UpdateCouponRequestModel request) throws EmptyException,  CouponException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new CouponException("此帳戶未登入，請重新確認");
        }

        AdminUpdateCouponRequestModel adminUpdateCouponRequestModel = new AdminUpdateCouponRequestModel();
        BeanUtils.copyProperties(request, adminUpdateCouponRequestModel);
        List<String> userIds = new ArrayList<>();
        userIds.add(userProfile.getUserId());
        adminUpdateCouponRequestModel.setUserIds(userIds);

        return adminCouponService.updateCoupon(couponId, adminUpdateCouponRequestModel);
    }
}
