package com.designershop.products;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

	private final ProductsService productsService;

	@PostMapping
	public ResponseEntity<String> createProduct(@RequestBody @Valid CreateProductRequestModel request)
			throws EmptyException, ProductException {
		String productName = productsService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
	}

//	@GetMapping
//	public ResponseEntity<List<UpdateProductRequestModel>> readAllProduct() {
//		List<UpdateProductRequestModel> response = productsService.readAllProduct();
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<UpdateProductRequestModel> readProduct(@PathVariable String id) {
//		UpdateProductRequestModel response = productsService.readProduct(id);
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateProduct(@PathVariable String id,
//			@RequestBody @Valid UpdateProductRequestModel request) {
//		String productName = productsService.updateProduct(id, request);
//		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteProduct(@PathVariable String id) {
//		String productName = productsService.deleteProduct(id);
//		return ResponseEntity.status(HttpStatus.CREATED).body(productName);
//	}
}
