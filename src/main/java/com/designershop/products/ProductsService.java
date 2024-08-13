package com.designershop.products;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.designershop.entities.Product;
import com.designershop.products.models.UpdateProductRequestModel;
import com.designershop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

	private final ProductRepository productRepository;

	public List<UpdateProductRequestModel> readAllProduct() {
		List<UpdateProductRequestModel> response = new ArrayList<>();

		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			UpdateProductRequestModel updateProductRequestModel = new UpdateProductRequestModel();
			BeanUtils.copyProperties(product, updateProductRequestModel);
			updateProductRequestModel.setPrice(product.getPrice().toString());
			updateProductRequestModel.setStockQuantity(Integer.toString(product.getStockQuantity()));
			updateProductRequestModel.setSoldQuantity(Integer.toString(product.getSoldQuantity()));
			updateProductRequestModel.setLikes(Integer.toString(product.getLikes()));
			updateProductRequestModel.setProductCategory(product.getProductCategory().getCategoryName());
			updateProductRequestModel.setProductBrand(product.getProductBrand().getBrand());
			response.add(updateProductRequestModel);
		}

		return response;
	}
}
