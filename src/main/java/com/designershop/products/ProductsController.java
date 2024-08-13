package com.designershop.products;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.products.models.UpdateProductRequestModel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

	private final ProductsService productsService;

	@GetMapping
	public ResponseEntity<List<UpdateProductRequestModel>> readAllProduct() {
		List<UpdateProductRequestModel> response = productsService.readAllProduct();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
