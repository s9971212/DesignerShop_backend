package com.designershop.orders;

import com.designershop.ecpay.EcpayService;
import com.designershop.entities.*;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateCartItemRequestModel;
import com.designershop.orders.models.CreateOrderRequestModel;
import com.designershop.orders.models.ReadCartItemResponseModel;
import com.designershop.orders.models.UpdateCartItemRequestModel;
import com.designershop.repositories.*;
import com.designershop.utils.DateTimeFormatUtil;
import com.designershop.utils.FormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final HttpSession session;
    private final EcpayService ecpayService;
    private final CartsService cartsService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public String createOrder(CreateOrderRequestModel request) throws EmptyException, UserException, ProductException, CartException, OrderException {
        List<String> itemIds = request.getItemIds();

        if (itemIds.isEmpty()) {
            throw new EmptyException("至少須選擇一個商品");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }

        Cart cart = cartRepository.findByUserId(userProfile.getUserId());
        if (Objects.isNull(cart)) {
            throw new CartException("此購物車不存在，請重新確認");
        }

        Order order = orderRepository.findMaxOrderId();
        String orderId = FormatUtil.orderIdGenerate(order);
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderItem> orderItems = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (String itemId : itemIds) {
            CartItem cartItem = cartItemRepository.findByItemIdAndCartId(itemId, cart.getCartId());
            if (Objects.isNull(cartItem)) {
                throw new CartException("此商品不存在購物車內，請重新確認");
            }

            Product product = productRepository.findByProductId(Integer.toString(cartItem.getProductId()));
            if (Objects.isNull(product)) {
                throw new ProductException("此商品不存在，請重新確認");
            }

            if (product.isDeleted()) {
                throw new ProductException("此商品已被刪除，請重新確認");
            }

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new ProductException("庫存數量不足，請重新確認");
            }

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

            OrderItem orderItem = new OrderItem();
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductId(product.getProductId());

            orderItems.add(orderItem);
            productNames.add(product.getProductName());
        }

        Order orderCreate = new Order();
        orderCreate.setOrderId(orderId);
        orderCreate.setTotalPrice(totalPrice);
        orderCreate.setCreatedDate(DateTimeFormatUtil.currentDateTime());
        orderCreate.setUserId(userProfile.getUserId());

        orderRepository.save(orderCreate);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(orderCreate);
            orderItemRepository.save(orderItem);
        }

        for (String itemId : itemIds) {
            cartsService.deleteCartItem(itemId);
        }

        String createdDate = DateTimeFormatUtil.localDateTimeFormat(orderCreate.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_SLASH_TIME);
        return ecpayService.ecpayCheckout(orderCreate.getOrderId(), createdDate, Integer.toString(orderCreate.getTotalPrice().intValue()), String.join("#", productNames));
    }
}
