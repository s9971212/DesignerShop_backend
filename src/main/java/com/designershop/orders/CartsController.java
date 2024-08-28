package com.designershop.orders;

import com.designershop.entities.Cart;
import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.orders.models.CreateCartItemRequestModel;
import com.designershop.products.models.CreateProductEvaluationRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartsController {

    private final CartsService cartsService;

    @PostMapping
    public ResponseEntity<String> createCart() throws EmptyException, UserException, CartException {
        Cart cart = cartsService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cart.getUserId());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> createCartItem(@PathVariable String productId, @RequestBody @Valid CreateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String userId = cartsService.createCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
