package com.designershop.products;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.ProductException;
import com.designershop.products.models.ReadProductResponseModel;

import lombok.RequiredArgsConstructor;

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

	@GetMapping("/{userId}")
	public ResponseEntity<List<ReadProductResponseModel>> readAllProductByUser(@PathVariable String userId) {
		List<ReadProductResponseModel> response = productsService.readAllProductByUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping
	public ResponseEntity<ReadProductResponseModel> readProduct(@RequestParam String productId)
			throws ProductException {
		ReadProductResponseModel response = productsService.readProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
