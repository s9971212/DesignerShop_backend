package com.designershop.admin.coupons;

import com.designershop.admin.coupons.models.AdminCreateCouponRequestModel;
import com.designershop.admin.products.models.AdminReadProductResponseModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.entities.*;
import com.designershop.enums.DiscountTypeEnum;
import com.designershop.exceptions.*;
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
public class AdminCouponsService {

    private final HttpSession session;
    private final UserProfileRepository userProfileRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductBrandRepository productBrandRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final CouponUserProfileRepository couponUserProfileRepository;
    private final CouponProductCategoryRepository couponProductCategoryRepository;
    private final CouponProductBrandRepository couponProductBrandRepository;
    private final CouponProductRepository couponProductRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createCoupon(AdminCreateCouponRequestModel request) throws EmptyException, CouponException {
        String code = request.getCode();
        String discountType = request.getDiscountType();
        String discountValueString = request.getDiscountValue();
        String minimumOrderPriceString = request.getMinimumOrderPrice();
        String usageLimitString = request.getUsageLimit();
        String couponDescription = request.getDescription();
        String image = request.getImage();
        String startDateString = request.getStartDate();
        String endDateString = request.getEndDate();
        String isActiveString = request.getIsActive();
        List<String> userIds = request.getUserIds();
        List<String> categoryIds = request.getCategoryIds();
        List<String> brandIds = request.getBrandIds();
        List<String> productIds = request.getProductIds();

        if (StringUtils.isBlank(discountType) || StringUtils.isBlank(discountValueString) || StringUtils.isBlank(startDateString)
                || StringUtils.isBlank(endDateString) || StringUtils.isBlank(isActiveString)) {
            throw new EmptyException("折扣類型、折扣值、開始日期、結束日期與是否啟用不得為空");
        }

        if (!discountValueString.matches("\\d+(\\.\\d+)?")) {
            throw new CouponException("折扣值只能有數字或小數點，請重新確認");
        }

        // 因應綠界價格只能正整數修改正規表示式，允許小數點可使用\d+(\.\d+)?
        if (StringUtils.isNotBlank(minimumOrderPriceString) && !minimumOrderPriceString.matches("\\d+")) {
            throw new CouponException("最低訂單金額只能有數字，請重新確認");
        }

        if (StringUtils.isNotBlank(usageLimitString) && !usageLimitString.matches("\\d+")) {
            throw new CouponException("使用次數只能有數字，請重新確認");
        }

        if (StringUtils.isBlank(code)) {
            code = RandomStringUtils.randomAlphanumeric(10);
        }
        BigDecimal discountValue = new BigDecimal(discountValueString);
        BigDecimal minimumOrderPrice = null;
        if (StringUtils.isNotBlank(minimumOrderPriceString)) {
            minimumOrderPrice = new BigDecimal(minimumOrderPriceString);
        }
        Integer usageLimit = null;
        if (StringUtils.isNotBlank(usageLimitString)) {
            usageLimit = Integer.parseInt(usageLimitString);
        }
        LocalDateTime startDate = DateTimeFormatUtil.localDateTimeFormat(startDateString, DateTimeFormatUtil.FULL_DATE_DASH_TIME);
        LocalDateTime endDate = DateTimeFormatUtil.localDateTimeFormat(endDateString, DateTimeFormatUtil.FULL_DATE_DASH_TIME);
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        boolean isActive = StringUtils.equals("Y", isActiveString);

        Coupon couponCreate = new Coupon();
        couponCreate.setCode(code);
        couponCreate.setDiscountType(DiscountTypeEnum.valueOf(discountType.toUpperCase()));
        couponCreate.setDiscountValue(discountValue);
        couponCreate.setMinimumOrderPrice(minimumOrderPrice);
        couponCreate.setUsageLimit(usageLimit);
        couponCreate.setDescription(couponDescription);
        couponCreate.setImage(image);
        couponCreate.setStartDate(startDate);
        couponCreate.setEndDate(endDate);
        couponCreate.setCreatedDate(currentDateTime);
        couponCreate.setUpdatedUser(userProfile.getUserId());
        couponCreate.setUpdatedDate(currentDateTime);
        couponCreate.setActive(isActive);

        couponRepository.save(couponCreate);

        validateCouponPermission(couponCreate.getCouponId(), userIds, categoryIds, brandIds, productIds);

        return code;
    }

    public void validateCouponPermission(int couponId, List<String> userIds, List<String> categoryIds, List<String> brandIds, List<String> productIds) throws CouponException {
        if (!userIds.isEmpty()) {
            validateUserIds(couponId, userIds);
        }

        if (!categoryIds.isEmpty()) {
            validateCategoryIds(couponId, categoryIds);
        }

        if (!brandIds.isEmpty()) {
            validateBrandIds(couponId, brandIds);
        }

        if (!productIds.isEmpty()) {
            validateProductIds(couponId, productIds);
        }
    }

    private void validateUserIds(int couponId, List<String> userIds) throws CouponException {
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

            CouponUserProfileId couponUserProfileId = new CouponUserProfileId();
            couponUserProfileId.setCouponId(couponId);
            couponUserProfileId.setUserId(userProfile.getUserId());

            CouponUserProfile couponUserProfile = new CouponUserProfile();
            couponUserProfile.setId(couponUserProfileId);
            couponUserProfileRepository.save(couponUserProfile);
        }
    }

    private void validateCategoryIds(int couponId, List<String> categoryIds) throws CouponException {
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryIds(categoryIds);
        Map<Integer, ProductCategory> productCategoryMap = productCategories.stream().collect(Collectors.toMap(ProductCategory::getCategoryId, ProductCategory -> ProductCategory));
        for (String categoryId : categoryIds) {
            ProductCategory productCategory = productCategoryMap.get(Integer.parseInt(categoryId));
            if (Objects.isNull(productCategory)) {
                throw new CouponException("某些商品類別不存在，請重新確認");
            }

            CouponProductCategoryId couponProductCategoryId = new CouponProductCategoryId();
            couponProductCategoryId.setCouponId(couponId);
            couponProductCategoryId.setCategoryId(productCategory.getCategoryId());

            CouponProductCategory couponProductCategory = new CouponProductCategory();
            couponProductCategory.setId(couponProductCategoryId);
            couponProductCategoryRepository.save(couponProductCategory);
        }
    }

    private void validateBrandIds(int couponId, List<String> brandIds) throws CouponException {
        List<ProductBrand> productBrands = productBrandRepository.findByBrandIds(brandIds);
        Map<Integer, ProductBrand> productBrandMap = productBrands.stream().collect(Collectors.toMap(ProductBrand::getBrandId, ProductBrand -> ProductBrand));
        for (String brandId : brandIds) {
            ProductBrand productBrand = productBrandMap.get(Integer.parseInt(brandId));
            if (Objects.isNull(productBrand)) {
                throw new CouponException("某些品牌不存在，請重新確認");
            }

            CouponProductBrandId couponProductBrandId = new CouponProductBrandId();
            couponProductBrandId.setCouponId(couponId);
            couponProductBrandId.setBrandId(productBrand.getBrandId());

            CouponProductBrand couponProductBrand = new CouponProductBrand();
            couponProductBrand.setId(couponProductBrandId);
            couponProductBrandRepository.save(couponProductBrand);
        }
    }

    private void validateProductIds(int couponId, List<String> productIds) throws CouponException {
        List<Product> products = productRepository.findByProductIds(productIds);
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductId, Product -> Product));
        for (String productId : productIds) {
            Product product = productMap.get(Integer.parseInt(productId));
            if (Objects.isNull(product)) {
                throw new CouponException("某些商品不存在，請重新確認");
            }

            CouponProductId couponProductId = new CouponProductId();
            couponProductId.setCouponId(couponId);
            couponProductId.setProductId(product.getProductId());

            CouponProduct couponProduct = new CouponProduct();
            couponProduct.setId(couponProductId);
            couponProductRepository.save(couponProduct);
        }
    }
}
