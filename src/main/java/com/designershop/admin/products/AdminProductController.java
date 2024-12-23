package com.designershop.admin.products;

import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.admin.products.models.AdminReadProductResponseModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> createProduct(@PathVariable String userId, @RequestBody @Valid AdminCreateProductRequestModel request)
            throws EmptyException, ProductException {
        String productName = adminProductService.createProduct(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<AdminReadProductResponseModel>> readAllProductByUser(@PathVariable String userId) throws ProductException {
        List<AdminReadProductResponseModel> response = adminProductService.readAllProductByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminReadProductResponseModel> readProduct(@PathVariable String id) throws ProductException {
        AdminReadProductResponseModel response = adminProductService.readProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody @Valid AdminUpdateProductRequestModel request)
            throws EmptyException, ProductException {
        String productName = adminProductService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) throws ProductException {
        String productName = adminProductService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productName);
    }
}
