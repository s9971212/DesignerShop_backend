package com.designershop.products.models;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadProductResponseModel {

	@NotBlank
	private String category;

	@NotBlank
	private String brand;

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

	@NotEmpty
	private List<String> images;

	@NotBlank
	private String createdDate;
}
