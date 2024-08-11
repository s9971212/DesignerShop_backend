package com.designershop.products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.designershop.entities.Product;
import com.designershop.entities.ProductImage;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.repositories.ProductImageRepository;
import com.designershop.repositories.ProductRepository;
import com.designershop.utils.DateTimeFormatUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

	private final HttpSession session;
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;

	public String createProduct(CreateProductRequestModel request) throws EmptyException, ProductException {
		String productName = request.getProductName();
		String productDescription = request.getProductDescription();
		String priceString = request.getPrice();
		String stockQuantityString = request.getStockQuantity();
		List<String> images = request.getImages();
		String termsCheckBox = request.getTermsCheckBox();

		if (StringUtils.isBlank(productName) || StringUtils.isBlank(priceString)
				|| StringUtils.isBlank(stockQuantityString) || StringUtils.isBlank(termsCheckBox)) {
			throw new EmptyException("商品名稱、價格、庫存數量與條款確認不得為空");
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

		Product productCreate = new Product();
		productCreate.setProductName(productName);
		productCreate.setProductDescription(productDescription);
		productCreate.setPrice(price);
		productCreate.setStockQuantity(stockQuantity);
		productCreate.setCreatedDate(currentDateTime);
		productCreate.setUpdatedDate(currentDateTime);
		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
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
}
