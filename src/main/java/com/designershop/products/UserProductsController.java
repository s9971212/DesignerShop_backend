package com.designershop.products;

import com.designershop.entities.ProductLikes;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.CreateProductEvaluationRequestModel;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.ReadProductResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.designershop.exceptions.ProductException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RestController
@RequestMapping("/api/user/products")
@RequiredArgsConstructor
public class UserProductsController {

    private final UserProductsService userProductsService;

    @GetMapping("/{id}")
    public ResponseEntity<String> readProductLikes(@PathVariable String id) throws ProductException {
        String response = userProductsService.readProductLikes(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProductLikes(@PathVariable String id) throws ProductException {
        String productName = userProductsService.updateProductLikes(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productName);
    }
}
