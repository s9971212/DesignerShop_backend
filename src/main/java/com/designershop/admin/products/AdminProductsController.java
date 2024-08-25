package com.designershop.admin.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductsController {

    private final AdminProductsService adminProductsService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> createProduct(@PathVariable String userId,
                                                @RequestBody @Valid AdminCreateProductRequestModel request) throws EmptyException, ProductException {
        String productName = adminProductsService.createProduct(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id,
                                                @RequestBody @Valid AdminUpdateProductRequestModel request) throws EmptyException, ProductException {
        String productName = adminProductsService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) throws ProductException {
        String productName = adminProductsService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productName);
    }
}
