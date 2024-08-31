package com.designershop.orders;

import com.designershop.entities.Cart;
import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.orders.models.CreateCartItemRequestModel;
import com.designershop.orders.models.ReadCartItemResponseModel;
import com.designershop.orders.models.UpdateCartItemRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartsController {

    private final CartsService cartsService;

    @PostMapping
    public ResponseEntity<String> createCart() throws EmptyException, UserException, ProductException, CartException {
        Cart cart = cartsService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cart.getUserId());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> createCartItem(@PathVariable String productId, @RequestBody @Valid CreateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String userId = cartsService.createCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping
    public ResponseEntity<List<ReadCartItemResponseModel>> readAllCartItem() throws UserException, CartException {
        List<ReadCartItemResponseModel> response = cartsService.readAllCartItem();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateCartItem(@PathVariable String productId, @RequestBody @Valid UpdateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String userId = cartsService.updateCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
