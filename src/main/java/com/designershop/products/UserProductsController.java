package com.designershop.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.ProductException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/products")
@RequiredArgsConstructor
public class UserProductsController {

    private final UserProductsService userProductsService;

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductLikes(@PathVariable String id) throws ProductException {
        String productName = userProductsService.updateProductLikes(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }
}
