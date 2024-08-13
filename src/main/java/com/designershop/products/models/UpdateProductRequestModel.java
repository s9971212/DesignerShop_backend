package com.designershop.products.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequestModel {

	@NotBlank
	private String productName;

	private String productDescription;

	@NotBlank
	private String price;

	@NotBlank
	private String stockQuantity;

	@NotBlank
	private String soldQuantity;

	@NotBlank
	private String likes;

	@NotBlank
	private String productCategory;

	@NotBlank
	private String productBrand;
}
