package com.designershop.products;

import com.designershop.exceptions.ProductException;
import com.designershop.products.models.ReadProductResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // TODO 未來改成首頁顯示的商品
    //	@GetMapping
    //	public ResponseEntity<List<ReadProductRequestModel>> readAllProduct() {
    //		List<ReadProductRequestModel> response = productsService.readAllProduct();
    //		return ResponseEntity.status(HttpStatus.OK).body(response);
    //	}

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ReadProductResponseModel>> readAllProductByUser(@PathVariable String userId) throws ProductException {
        List<ReadProductResponseModel> response = productService.readAllProductByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadProductResponseModel> readProduct(@PathVariable String id) throws ProductException {
        ReadProductResponseModel response = productService.readProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
