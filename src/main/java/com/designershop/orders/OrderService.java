package com.designershop.orders;

import com.designershop.carts.CartService;
import com.designershop.ecpay.EcpayService;
import com.designershop.entities.*;
import com.designershop.enums.DiscountTypeEnum;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderRequestModel;
import com.designershop.orders.models.ReadOrderItemResponseModel;
import com.designershop.orders.models.ReadOrderResponseModel;
import com.designershop.repositories.*;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.FormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final HttpSession session;
    private final EcpayService ecpayService;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CouponRepository couponRepository;
    private final CouponUserProfileRepository couponUserProfileRepository;
    private final CouponProductCategoryRepository couponProductCategoryRepository;
    private final CouponProductBrandRepository couponProductBrandRepository;
    private final CouponProductRepository couponProductRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final OrderDeliveryRepository orderDeliveryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createOrder(String deliveryId, CreateOrderRequestModel request) throws EmptyException, CartException, OrderException {
        List<String> itemIds = request.getItemIds();
        List<String> couponIds = request.getCouponIds();

        if (StringUtils.isBlank(deliveryId)) {
            throw new EmptyException("地址、聯絡電話與聯絡人姓名不得為空");
        }

        if (itemIds.isEmpty()) {
            throw new EmptyException("至少須選擇一個商品");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new OrderException("此帳戶未登入，請重新確認");
        }

        Cart cart = cartRepository.findByUserId(userProfile.getUserId());
        if (Objects.isNull(cart)) {
            throw new OrderException("此購物車不存在，請重新確認");
        }

        OrderDelivery orderDelivery = orderDeliveryRepository.findByDeliveryId(Integer.parseInt(deliveryId));
        if (Objects.isNull(orderDelivery)) {
            throw new OrderException("此訂單配送不存在，請重新確認");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        Map<Product, BigDecimal> productPriceMap = new HashMap<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (String itemId : itemIds) {
            CartItem cartItem = cartItemRepository.findByItemIdAndCartId(Integer.parseInt(itemId), cart.getCartId());
            if (Objects.isNull(cartItem)) {
                throw new OrderException("此商品不存在購物車內，請重新確認");
            }

            Product product = productRepository.findByProductId(cartItem.getProductId());
            if (Objects.isNull(product)) {
                throw new OrderException("此商品不存在，請重新確認");
            }

            if (product.isDeleted()) {
                throw new OrderException("此商品已被刪除，請重新確認");
            }

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new OrderException("庫存數量不足，請重新確認");
            }

            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(price);

            OrderItem orderItem = new OrderItem();
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductId(product.getProductId());

            productPriceMap.putIfAbsent(product, price);
            orderItemList.add(orderItem);
            productNames.add(product.getName());
        }

        Order order = orderRepository.findMaxOrderId();
        String orderId = FormatUtil.orderIdGenerate(order);
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (String couponId : couponIds) {
            Coupon coupon = couponRepository.findByCouponId(Integer.parseInt(couponId));
            if (Objects.isNull(coupon) || !coupon.isActive() || LocalDateTime.now().isBefore(coupon.getStartDate()) || LocalDateTime.now().isAfter(coupon.getEndDate())) {
                throw new OrderException("此優惠券不存在，請重新確認");
            }

            List<CouponUserProfile> couponUserProfileList = couponUserProfileRepository.findAllByCouponId(Integer.parseInt(couponId));
            Set<String> userIds = couponUserProfileList.stream().map(CouponUserProfile -> CouponUserProfile.getId().getUserId()).collect(Collectors.toSet());
            List<CouponProductCategory> couponProductCategoryList = couponProductCategoryRepository.findAllByCouponId(Integer.parseInt(couponId));
            Set<Integer> categoryIds = couponProductCategoryList.stream().map(CouponProductCategory -> CouponProductCategory.getId().getCategoryId()).collect(Collectors.toSet());
            List<CouponProductBrand> couponProductBrandList = couponProductBrandRepository.findAllByCouponId(Integer.parseInt(couponId));
            Set<Integer> brandIds = couponProductBrandList.stream().map(CouponProductBrand -> CouponProductBrand.getId().getBrandId()).collect(Collectors.toSet());
            List<CouponProduct> couponProductList = couponProductRepository.findAllByCouponId(Integer.parseInt(couponId));
            Set<Integer> productIds = couponProductList.stream().map(CouponProduct -> CouponProduct.getId().getProductId()).collect(Collectors.toSet());

            BigDecimal price = BigDecimal.ZERO;
            for (Map.Entry<Product, BigDecimal> productEntry : productPriceMap.entrySet()) {
                Product product = productEntry.getKey();

                if ((!userIds.isEmpty() && !userIds.contains(product.getUserId())) ||
                        (!categoryIds.isEmpty() && !categoryIds.contains(product.getProductCategory().getCategoryId())) ||
                        (!brandIds.isEmpty() && !brandIds.contains(product.getProductBrand().getBrandId())) ||
                        (!productIds.isEmpty() && !productIds.contains(product.getProductId()))) {
                    continue;
                }

                price = price.add(productEntry.getValue());
            }

            if (Objects.nonNull(coupon.getMinimumOrderPrice()) && price.compareTo(coupon.getMinimumOrderPrice()) < 0) {
                throw new OrderException("訂單金額未達優惠券使用的最低訂單金額");
            }

            BigDecimal discount = BigDecimal.ZERO;
            if (coupon.getDiscountType().equals(DiscountTypeEnum.PERCENTAGE)) {
                discount = price.multiply(coupon.getDiscountValue().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            } else if (coupon.getDiscountType().equals(DiscountTypeEnum.FIXED)) {
                discount = coupon.getDiscountValue();
            }

            if (discount.compareTo(price) > 0) {
                discount = price;
            }

            totalDiscount = totalDiscount.add(discount);

            List<CouponIssuance> couponIssuanceList = couponIssuanceRepository.findByUserIdAndCouponId(userProfile.getUserId(), coupon.getCouponId());
            CouponIssuance unusedCoupon = couponIssuanceList.stream().filter(couponIssuance -> !couponIssuance.isUsed()).findFirst().orElse(null);
            if (Objects.isNull(unusedCoupon)) {
                throw new OrderException("此優惠券已被使用，請重新確認");
            }

            unusedCoupon.setUsed(true);
            unusedCoupon.setUsedDate(currentDateTime);
            couponIssuanceRepository.save(unusedCoupon);

            CouponUsage couponUsage = new CouponUsage();
            couponUsage.setUsedDate(currentDateTime);
            couponUsage.setUserId(userProfile.getUserId());
            couponUsage.setOrderId(orderId);
            couponUsage.setCoupon(coupon);

            couponUsageRepository.save(couponUsage);
        }

        totalPrice = totalPrice.subtract(totalDiscount);
        if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            totalPrice = BigDecimal.ZERO;
        }

        Order orderCreate = new Order();
        orderCreate.setOrderId(orderId);
        orderCreate.setTotalPrice(totalPrice);
        orderCreate.setCreatedDate(currentDateTime);
        orderCreate.setFullAddress(orderDelivery.getFullAddress());
        orderCreate.setContactPhone(orderDelivery.getContactPhone());
        orderCreate.setContactName(orderDelivery.getContactName());
        orderCreate.setUserId(userProfile.getUserId());
        orderCreate.setOrderDelivery(orderDelivery);

        orderRepository.save(orderCreate);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder(orderCreate);
            orderItemRepository.save(orderItem);
        }

        for (String itemId : itemIds) {
            cartService.deleteCartItem(itemId);
        }

        String createdDate = DateTimeFormatUtil.localDateTimeFormat(orderCreate.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_SLASH_TIME);
        return ecpayService.ecpayCheckout(orderCreate.getOrderId(), createdDate, Integer.toString(orderCreate.getTotalPrice().intValue()), String.join("#", productNames));
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ReadOrderResponseModel> readAllOrder() throws OrderException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new OrderException("此帳戶未登入，請重新確認");
        }

        List<ReadOrderResponseModel> response = new ArrayList<>();

        List<Order> orderList = orderRepository.findAllByUserId(userProfile.getUserId());
        for (Order order : orderList) {
            ReadOrderResponseModel readOrderResponseModel = new ReadOrderResponseModel();
            BeanUtils.copyProperties(order, readOrderResponseModel);
            readOrderResponseModel.setTotalPrice(order.getTotalPrice().toString());

            List<ReadOrderItemResponseModel> orderItems = new ArrayList<>();

            List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(order.getOrderId());
            for (OrderItem orderItem : orderItemList) {
                ReadOrderItemResponseModel readOrderItemResponseModel = new ReadOrderItemResponseModel();
                readOrderItemResponseModel.setItemId(Integer.toString(orderItem.getItemId()));
                readOrderItemResponseModel.setPriceAtPurchase(orderItem.getPriceAtPurchase().toString());
                readOrderItemResponseModel.setQuantity(Integer.toString(orderItem.getQuantity()));
                readOrderItemResponseModel.setProductId(Integer.toString(orderItem.getProductId()));

                Product product = productRepository.findByProductId(orderItem.getProductId());
                BeanUtils.copyProperties(product, readOrderItemResponseModel);
                readOrderItemResponseModel.setPrice(product.getPrice().toString());
                readOrderItemResponseModel.setOriginalPrice(product.getOriginalPrice().toString());
                readOrderItemResponseModel.setImage(product.getProductImages().get(0).getImage());

                orderItems.add(readOrderItemResponseModel);
            }
            readOrderResponseModel.setOrderItems(orderItems);

            response.add(readOrderResponseModel);
        }

        return response;
    }
}
