package com.designershop.products;

import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productLikes")
@RequiredArgsConstructor
public class ProductLikesController {

    private final ProductLikesService productLikesService;

    @GetMapping("/{productId}")
    public ResponseEntity<String> readProductLikes(@PathVariable String productId) throws ProductException {
        String response = productLikesService.readProductLikes(productId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> updateProductLikes(@PathVariable String productId) throws UserException, ProductException {
        String productName = productLikesService.updateProductLikes(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }
}
