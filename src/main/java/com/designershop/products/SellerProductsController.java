package com.designershop.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seller/products")
@RequiredArgsConstructor
public class SellerProductsController {

	private final SellerProductsService sellerProductsService;

	@PostMapping
	public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductRequestModel request)
			throws EmptyException, UserException, ProductException {
		String productName = sellerProductsService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable String id,
			@RequestBody @Valid UpdateProductRequestModel request)
			throws EmptyException, UserException, ProductException {
		String productName = sellerProductsService.updateProduct(id, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable String id) throws UserException, ProductException {
		String productName = sellerProductsService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
	}
}
