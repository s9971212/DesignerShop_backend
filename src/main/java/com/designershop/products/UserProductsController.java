package com.designershop.products;

import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/products")
@RequiredArgsConstructor
public class UserProductsController {

    private final UserProductsService userProductsService;

    @GetMapping
    public ResponseEntity<String> readProductLikes(@RequestParam String productId) throws ProductException {
        String response = userProductsService.readProductLikes(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> updateProductLikes(@PathVariable String productId) throws UserException, ProductException {
        String productName = userProductsService.updateProductLikes(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }
}
