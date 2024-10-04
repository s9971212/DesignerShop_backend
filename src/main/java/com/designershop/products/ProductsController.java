package com.designershop.products;

import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.ReadProductResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    // TODO 未來改成首頁顯示的商品
    //	@GetMapping
    //	public ResponseEntity<List<ReadProductRequestModel>> readAllProduct() {
    //		List<ReadProductRequestModel> response = productsService.readAllProduct();
    //		return ResponseEntity.status(HttpStatus.OK).body(response);
    //	}

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ReadProductResponseModel>> readAllProductByUser(@PathVariable String userId) throws UserException {
        List<ReadProductResponseModel> response = productsService.readAllProductByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadProductResponseModel> readProduct(@PathVariable String id) throws ProductException {
        ReadProductResponseModel response = productsService.readProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
