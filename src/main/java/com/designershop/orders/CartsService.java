package com.designershop.orders;

import com.designershop.entities.*;
import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.orders.models.CreateCartItemRequestModel;
import com.designershop.repositories.CartItemRepository;
import com.designershop.repositories.CartRepository;
import com.designershop.repositories.ProductRepository;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartsService {

    private final HttpSession session;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart createCart() throws EmptyException, UserException, CartException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }

        Cart cart = cartRepository.findByUserId(userProfile.getUserId());
        if (Objects.nonNull(cart)) {
            throw new CartException("此帳戶已有購物車，請重新確認");
        }

        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

        Cart cartCreate = new Cart();
        cartCreate.setCreatedDate(currentDateTime);
        cartCreate.setUpdatedDate(currentDateTime);
        cartCreate.setUserId(userProfile.getUserId());

        cartRepository.save(cartCreate);

        return cartCreate;
    }

    @Transactional
    public String createCartItem(String productId, CreateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String quantityString = request.getQuantity();

        if (StringUtils.isBlank(quantityString)) {
            throw new EmptyException("數量不得為空");
        }

        if (!quantityString.matches("\\d+")) {
            throw new CartException("數量只能有數字，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }

        Product product = productRepository.findByProductId(productId);
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        int quantity = Integer.parseInt(quantityString);
        if (product.getStockQuantity() < quantity) {
            throw new CartException("庫存數量不足，請重新確認");
        }

        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

        Cart cart = cartRepository.findByUserId(userProfile.getUserId());
        if (Objects.isNull(cart)) {
            cart = createCart();
        } else {
            cart.setUpdatedDate(currentDateTime);
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(), productId);
        if (Objects.isNull(cartItem)) {
            cartItem = new CartItem();
            cartItem.setAddedDate(currentDateTime);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return userProfile.getUserId();
    }
}
