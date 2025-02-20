package com.designershop.carts;

import com.designershop.carts.models.CreateCartItemRequestModel;
import com.designershop.carts.models.ReadCartItemRequestModel;
import com.designershop.carts.models.ReadCartItemResponseModel;
import com.designershop.carts.models.UpdateCartItemRequestModel;
import com.designershop.entities.Cart;
import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart() throws CartException {
        Cart cart = cartService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cart.getUserId());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> createCartItem(@PathVariable String productId, @RequestBody @Valid CreateCartItemRequestModel request)
            throws EmptyException, CartException {
        String userId = cartService.createCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadCartItemResponseModel>> readAllCartItem() throws CartException {
        List<ReadCartItemResponseModel> response = cartService.readAllCartItem();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadCartItemResponseModel>> readCartItemByItemIds(@RequestBody @Valid ReadCartItemRequestModel request)
            throws EmptyException, CartException {
        List<ReadCartItemResponseModel> response = cartService.readCartItemByItemIds(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateCartItem(@PathVariable String productId, @RequestBody @Valid UpdateCartItemRequestModel request)
            throws EmptyException, CartException {
        String userId = cartService.updateCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String id) throws CartException {
        String userId = cartService.deleteCartItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
