package com.designershop.admin.coupons;

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

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class AdminCouponService {

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

    public String createCoupon(AdminCreateCouponRequestModel request) throws EmptyException, CouponException {
        String code = request.getCode();
        String discountType = request.getDiscountType();
        String discountValueString = request.getDiscountValue();
        String minimumOrderPriceString = request.getMinimumOrderPrice();
        String issuanceLimitString = request.getIssuanceLimit();
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
            throw new EmptyException("折扣類型、折扣值、開始時間、結束時間與是否啟用不得為空");
        }
        if (!discountValueString.matches("\\d+(\\.\\d+)?")) {
            throw new CouponException("折扣值只能有數字或小數點，請重新確認");
        }
        // 因應綠界價格只能正整數修改正規表示式，允許小數點可使用\d+(\.\d+)?
        if (StringUtils.isNotBlank(minimumOrderPriceString) && !minimumOrderPriceString.matches("\\d+")) {
            throw new CouponException("最低訂單金額只能有數字，請重新確認");
        }
        if (StringUtils.isNotBlank(issuanceLimitString) && !issuanceLimitString.matches("\\d+")) {
            throw new CouponException("發放次數只能有數字，請重新確認");
        }
        if (StringUtils.isNotBlank(usageLimitString) && !usageLimitString.matches("\\d+")) {
            throw new CouponException("使用次數只能有數字，請重新確認");
        }
        if (StringUtils.isBlank(code)) {
            code = RandomStringUtils.randomAlphanumeric(10);
        }
        Coupon coupon = couponRepository.findByCode(code);
        if (Objects.nonNull(coupon)) {
            throw new CouponException("此優惠券代碼已被使用，請重新確認");
        }

        BigDecimal discountValue = new BigDecimal(discountValueString);
        BigDecimal minimumOrderPrice = null;
        if (StringUtils.isNotBlank(minimumOrderPriceString)) {
            minimumOrderPrice = new BigDecimal(minimumOrderPriceString);
        }
        Integer issuanceLimit = null;
        if (StringUtils.isNotBlank(issuanceLimitString)) {
            issuanceLimit = Integer.parseInt(issuanceLimitString);
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
        couponCreate.setIssuanceLimit(issuanceLimit);
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
        createCouponPermission(couponCreate.getCouponId(), userIds, categoryIds, brandIds, productIds);
        return code;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<AdminReadCouponResponseModel> readAllCoupon() {
        List<AdminReadCouponResponseModel> response = new ArrayList<>();

        List<Coupon> couponList = couponRepository.findAll();
        for (Coupon coupon : couponList) {
            int couponId = coupon.getCouponId();

            AdminReadCouponResponseModel adminReadCouponResponseModel = new AdminReadCouponResponseModel();
            BeanUtils.copyProperties(coupon, adminReadCouponResponseModel);
            adminReadCouponResponseModel.setCouponId(Integer.toString(couponId));
            adminReadCouponResponseModel.setDiscountType(coupon.getDiscountType().name().toLowerCase());
            adminReadCouponResponseModel.setDiscountValue(coupon.getDiscountValue().toString());
            if (Objects.nonNull(coupon.getMinimumOrderPrice())) {
                adminReadCouponResponseModel.setMinimumOrderPrice(coupon.getMinimumOrderPrice().toString());
            }
            if (Objects.nonNull(coupon.getIssuanceLimit())) {
                adminReadCouponResponseModel.setIssuanceLimit(Integer.toString(coupon.getIssuanceLimit()));
            }
            if (Objects.nonNull(coupon.getUsageLimit())) {
                adminReadCouponResponseModel.setUsageLimit(Integer.toString(coupon.getUsageLimit()));
            }
            adminReadCouponResponseModel.setStartDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getStartDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            adminReadCouponResponseModel.setEndDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getEndDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            adminReadCouponResponseModel.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(coupon.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            adminReadCouponResponseModel.setIsActive(coupon.isActive() ? "Y" : "N");
            List<CouponUserProfile> couponUserProfileList = couponUserProfileRepository.findAllByCouponId(couponId);
            List<String> userIds = couponUserProfileList.stream().map(couponUserProfile -> couponUserProfile.getId().getUserId()).toList();
            adminReadCouponResponseModel.setUserIds(userIds);
            List<CouponProductCategory> couponProductCategoryList = couponProductCategoryRepository.findAllByCouponId(couponId);
            List<String> categoryIds = couponProductCategoryList.stream().map(couponProductCategory -> Integer.toString(couponProductCategory.getId().getCategoryId())).toList();
            adminReadCouponResponseModel.setCategoryIds(categoryIds);
            List<CouponProductBrand> couponProductBrandList = couponProductBrandRepository.findAllByCouponId(couponId);
            List<String> brandIds = couponProductBrandList.stream().map(couponProductBrand -> Integer.toString(couponProductBrand.getId().getBrandId())).toList();
            adminReadCouponResponseModel.setBrandIds(brandIds);
            List<CouponProduct> couponProductList = couponProductRepository.findAllByCouponId(couponId);
            List<String> productIds = couponProductList.stream().map(couponProduct -> Integer.toString(couponProduct.getId().getProductId())).toList();
            adminReadCouponResponseModel.setProductIds(productIds);
            response.add(adminReadCouponResponseModel);
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public AdminReadCouponResponseModel readCoupon(String couponId) throws CouponException {
        Coupon coupon = couponRepository.findByCouponId(Integer.parseInt(couponId));
        if (Objects.isNull(coupon)) {
            throw new CouponException("此優惠券不存在，請重新確認");
        }

        AdminReadCouponResponseModel response = new AdminReadCouponResponseModel();
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
        List<CouponUserProfile> couponUserProfileList = couponUserProfileRepository.findAllByCouponId(Integer.parseInt(couponId));
        List<String> userIds = couponUserProfileList.stream().map(couponUserProfile -> couponUserProfile.getId().getUserId()).toList();
        response.setUserIds(userIds);
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

    public String updateCoupon(String couponId, AdminUpdateCouponRequestModel request) throws EmptyException, CouponException {
        String code = request.getCode();
        String discountType = request.getDiscountType();
        String discountValueString = request.getDiscountValue();
        String minimumOrderPriceString = request.getMinimumOrderPrice();
        String issuanceLimitString = request.getIssuanceLimit();
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
            throw new EmptyException("折扣類型、折扣值、開始時間、結束時間與是否啟用不得為空");
        }
        if (!discountValueString.matches("\\d+(\\.\\d+)?")) {
            throw new CouponException("折扣值只能有數字或小數點，請重新確認");
        }
        // 因應綠界價格只能正整數修改正規表示式，允許小數點可使用\d+(\.\d+)?
        if (StringUtils.isNotBlank(minimumOrderPriceString) && !minimumOrderPriceString.matches("\\d+")) {
            throw new CouponException("最低訂單金額只能有數字，請重新確認");
        }
        if (StringUtils.isNotBlank(issuanceLimitString) && !issuanceLimitString.matches("\\d+")) {
            throw new CouponException("發放次數只能有數字，請重新確認");
        }
        if (StringUtils.isNotBlank(usageLimitString) && !usageLimitString.matches("\\d+")) {
            throw new CouponException("使用次數只能有數字，請重新確認");
        }
        Coupon coupon = couponRepository.findByCode(code);
        if (Objects.nonNull(coupon)) {
            throw new CouponException("此優惠券代碼已被使用，請重新確認");
        }
        coupon = couponRepository.findByCouponId(Integer.parseInt(couponId));
        if (Objects.isNull(coupon)) {
            throw new CouponException("此優惠券不存在，請重新確認");
        }

        if (StringUtils.isBlank(code)) {
            code = coupon.getCode();
        }
        BigDecimal discountValue = new BigDecimal(discountValueString);
        BigDecimal minimumOrderPrice = null;
        if (StringUtils.isNotBlank(minimumOrderPriceString)) {
            minimumOrderPrice = new BigDecimal(minimumOrderPriceString);
        }
        Integer issuanceLimit = null;
        if (StringUtils.isNotBlank(issuanceLimitString)) {
            issuanceLimit = Integer.parseInt(issuanceLimitString);
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

        coupon.setCode(code);
        coupon.setDiscountType(DiscountTypeEnum.valueOf(discountType.toUpperCase()));
        coupon.setDiscountValue(discountValue);
        coupon.setMinimumOrderPrice(minimumOrderPrice);
        coupon.setIssuanceLimit(issuanceLimit);
        coupon.setUsageLimit(usageLimit);
        coupon.setDescription(couponDescription);
        coupon.setImage(image);
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        coupon.setUpdatedUser(userProfile.getUserId());
        coupon.setUpdatedDate(currentDateTime);
        coupon.setActive(isActive);
        couponRepository.save(coupon);
        updateCouponPermission(coupon.getCouponId(), userIds, categoryIds, brandIds, productIds);
        return code;
    }

    public void createCouponPermission(int couponId, List<String> userIds, List<String> categoryIds, List<String> brandIds, List<String> productIds)
            throws CouponException {
        createCouponUserProfile(couponId, userIds);
        createCouponProductCategory(couponId, categoryIds);
        createCouponProductBrand(couponId, brandIds);
        createCouponProduct(couponId, productIds);
    }

    public void updateCouponPermission(int couponId, List<String> userIds, List<String> categoryIds, List<String> brandIds, List<String> productIds)
            throws CouponException {
        List<CouponUserProfile> couponUserProfileList = couponUserProfileRepository.findAllByCouponId(couponId);
        Set<String> existingUserIds = couponUserProfileList.stream().map(CouponUserProfile -> CouponUserProfile.getId().getUserId()).collect(Collectors.toSet());
        List<CouponProductCategory> couponProductCategoryList = couponProductCategoryRepository.findAllByCouponId(couponId);
        Set<String> existingCategoryIds = couponProductCategoryList.stream().map(CouponProductCategory -> Integer.toString(CouponProductCategory.getId().getCategoryId())).collect(Collectors.toSet());
        List<CouponProductBrand> couponProductBrandList = couponProductBrandRepository.findAllByCouponId(couponId);
        Set<String> existingBrandIds = couponProductBrandList.stream().map(CouponProductBrand -> Integer.toString(CouponProductBrand.getId().getBrandId())).collect(Collectors.toSet());
        List<CouponProduct> couponProductList = couponProductRepository.findAllByCouponId(couponId);
        Set<String> existingProductIds = couponProductList.stream().map(CouponProduct -> Integer.toString(CouponProduct.getId().getProductId())).collect(Collectors.toSet());

        Set<String> userIdsToDelete = new HashSet<>(existingUserIds);
        existingUserIds.retainAll(userIds);
        userIds.removeAll(existingUserIds);
        userIdsToDelete.removeAll(existingUserIds);

        Set<String> categoryIdsToDelete = new HashSet<>(existingCategoryIds);
        existingCategoryIds.retainAll(categoryIds);
        categoryIds.removeAll(existingCategoryIds);
        categoryIdsToDelete.removeAll(existingCategoryIds);

        Set<String> brandIdsToDelete = new HashSet<>(existingBrandIds);
        existingBrandIds.retainAll(brandIds);
        brandIds.removeAll(existingBrandIds);
        brandIdsToDelete.removeAll(existingBrandIds);

        Set<String> productIdsToDelete = new HashSet<>(existingProductIds);
        existingProductIds.retainAll(productIds);
        productIds.removeAll(existingProductIds);
        productIdsToDelete.removeAll(existingProductIds);

        createCouponPermission(couponId, userIds, categoryIds, brandIds, productIds);
        deleteCouponPermission(couponId, userIdsToDelete, categoryIdsToDelete, brandIdsToDelete, productIdsToDelete);
    }

    public void deleteCouponPermission(int couponId, Set<String> userIds, Set<String> categoryIds, Set<String> brandIds, Set<String> productIds)
            throws CouponException {
        deleteCouponUserProfile(couponId, userIds);
        deleteCouponProductCategory(couponId, categoryIds);
        deleteCouponProductBrand(couponId, brandIds);
        deleteCouponProduct(couponId, productIds);
    }

    private void createCouponUserProfile(int couponId, List<String> userIds) throws CouponException {
        List<UserProfile> userProfileList = userProfileRepository.findByUserIds(userIds);
        Map<String, UserProfile> userProfileMap = userProfileList.stream().collect(Collectors.toMap(UserProfile::getUserId, userProfile -> userProfile));
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

    private void createCouponProductCategory(int couponId, List<String> categoryIds) throws CouponException {
        List<ProductCategory> productCategoryList = productCategoryRepository.findByCategoryIds(categoryIds);
        Map<Integer, ProductCategory> productCategoryMap = productCategoryList.stream().collect(Collectors.toMap(ProductCategory::getCategoryId, ProductCategory -> ProductCategory));
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

    private void createCouponProductBrand(int couponId, List<String> brandIds) throws CouponException {
        List<ProductBrand> productBrandList = productBrandRepository.findByBrandIds(brandIds);
        Map<Integer, ProductBrand> productBrandMap = productBrandList.stream().collect(Collectors.toMap(ProductBrand::getBrandId, ProductBrand -> ProductBrand));
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

    private void createCouponProduct(int couponId, List<String> productIds) throws CouponException {
        List<Product> productList = productRepository.findByProductIds(productIds);
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, Product -> Product));
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

    private void deleteCouponUserProfile(int couponId, Set<String> userIds) throws CouponException {
        for (String userId : userIds) {
            CouponUserProfile couponUserProfile = couponUserProfileRepository.findByCouponIdAndUserId(couponId, userId);
            if (Objects.isNull(couponUserProfile)) {
                throw new CouponException("某些帳戶不存在，請重新確認");
            }

            couponUserProfileRepository.delete(couponUserProfile);
        }
    }

    private void deleteCouponProductCategory(int couponId, Set<String> categoryIds) throws CouponException {
        for (String categoryId : categoryIds) {
            CouponProductCategory couponProductCategory = couponProductCategoryRepository.findByCouponIdAndCategoryId(couponId, Integer.parseInt(categoryId));
            if (Objects.isNull(couponProductCategory)) {
                throw new CouponException("某些商品類別不存在，請重新確認");
            }

            couponProductCategoryRepository.delete(couponProductCategory);
        }
    }

    private void deleteCouponProductBrand(int couponId, Set<String> brandIds) throws CouponException {
        for (String brandId : brandIds) {
            CouponProductBrand couponProductBrand = couponProductBrandRepository.findByCouponIdAndBrandId(couponId, Integer.parseInt(brandId));
            if (Objects.isNull(couponProductBrand)) {
                throw new CouponException("某些品牌不存在，請重新確認");
            }

            couponProductBrandRepository.save(couponProductBrand);
        }
    }

    private void deleteCouponProduct(int couponId, Set<String> productIds) throws CouponException {
        for (String productId : productIds) {
            CouponProduct couponProduct = couponProductRepository.findByCouponIdAndProductId(couponId, Integer.parseInt(productId));
            if (Objects.isNull(couponProduct)) {
                throw new CouponException("某些商品不存在，請重新確認");
            }

            couponProductRepository.save(couponProduct);
        }
    }
}
