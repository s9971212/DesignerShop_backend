package com.designershop.carts;

import com.designershop.carts.models.ReadCartItemRequestModel;
import com.designershop.entities.Cart;
import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.carts.models.CreateCartItemRequestModel;
import com.designershop.carts.models.ReadCartItemResponseModel;
import com.designershop.carts.models.UpdateCartItemRequestModel;
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
    public ResponseEntity<String> createCart() throws UserException, CartException {
        Cart cart = cartsService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cart.getUserId());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> createCartItem(@PathVariable String productId, @RequestBody @Valid CreateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String userId = cartsService.createCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadCartItemResponseModel>> readAllCartItem() throws UserException, CartException {
        List<ReadCartItemResponseModel> response = cartsService.readAllCartItem();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReadCartItemResponseModel>> readCartItemByItemIds(@RequestBody @Valid ReadCartItemRequestModel request)
            throws EmptyException, UserException, CartException {
        List<ReadCartItemResponseModel> response = cartsService.readCartItemByItemIds(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateCartItem(@PathVariable String productId, @RequestBody @Valid UpdateCartItemRequestModel request)
            throws EmptyException, UserException, ProductException, CartException {
        String userId = cartsService.updateCartItem(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable String id) throws UserException, CartException {
        String userId = cartsService.deleteCartItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
