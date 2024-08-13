package com.designershop.products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.designershop.entities.Product;
import com.designershop.entities.ProductBrand;
import com.designershop.entities.ProductCategory;
import com.designershop.entities.ProductImage;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import com.designershop.repositories.ProductBrandRepository;
import com.designershop.repositories.ProductCategoryRepository;
import com.designershop.repositories.ProductImageRepository;
import com.designershop.repositories.ProductRepository;
import com.designershop.utils.DateTimeFormatUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProductsService {

	private final HttpSession session;
	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	private final ProductBrandRepository productBrandRepository;
	private final ProductImageRepository productImageRepository;

	public String createProduct(CreateProductRequestModel request) throws EmptyException, ProductException {
		String category = request.getCategory();
		String brand = request.getBrand();
		String productName = request.getProductName();
		String productDescription = request.getProductDescription();
		String priceString = request.getPrice();
		String stockQuantityString = request.getStockQuantity();
		List<String> images = request.getImages();
		String termsCheckBox = request.getTermsCheckBox();

		if (StringUtils.isBlank(category) || StringUtils.isBlank(brand) || StringUtils.isBlank(productName)
				|| StringUtils.isBlank(priceString) || StringUtils.isBlank(stockQuantityString)
				|| StringUtils.isBlank(termsCheckBox)) {
			throw new EmptyException("商品類別、品牌、商品名稱、價格、庫存數量與條款確認不得為空");
		}

		if (images.isEmpty()) {
			throw new EmptyException("商品圖片至少須有一張");
		}

		if (!priceString.matches("\\d+(\\.\\d+)?")) {
			throw new ProductException("價格只能有數字或小數點，請重新確認");
		}

		if (!stockQuantityString.matches("\\d+")) {
			throw new ProductException("庫存數量只能有數字，請重新確認");
		}

		BigDecimal price = new BigDecimal(priceString);
		int stockQuantity = Integer.parseInt(stockQuantityString);
		LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();

		ProductCategory productCategory = productCategoryRepository.findByCategoryName(category);

		ProductBrand productBrand = productBrandRepository.findByBrandIgnoreCase(brand);
		if (Objects.isNull(productBrand)) {
			productBrand = new ProductBrand();
			productBrand.setBrand(brand);
			productBrandRepository.save(productBrand);
		}

		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");

		Product productCreate = new Product();
		productCreate.setProductName(productName);
		productCreate.setProductDescription(productDescription);
		productCreate.setPrice(price);
		productCreate.setStockQuantity(stockQuantity);
		productCreate.setCreatedDate(currentDateTime);
		productCreate.setUpdatedDate(currentDateTime);
		productCreate.setProductCategory(productCategory);
		productCreate.setProductBrand(productBrand);
		productCreate.setUserProfile(sessionUserProfile);

		productRepository.save(productCreate);

		for (String image : images) {
			ProductImage productImage = new ProductImage();
			productImage.setImage(image);
			productImage.setProduct(productCreate);
			productImageRepository.save(productImage);
		}

		return productName;
	}

	public List<UpdateProductRequestModel> readAllProductByUser(String userId) {
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
