package com.designershop.products;

import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductRequestModel request) throws EmptyException, ProductException {
        String productName = sellerProductService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody @Valid UpdateProductRequestModel request)
            throws EmptyException,  ProductException {
        String productName = sellerProductService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) throws  ProductException {
        String productName = sellerProductService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productName);
    }
}
