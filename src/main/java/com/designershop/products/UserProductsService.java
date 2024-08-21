package com.designershop.products;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.designershop.admin.products.AdminProductsService;
import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import com.designershop.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProductsService {

	private final HttpSession session;
	private final AdminProductsService adminProductsService;
	private final ProductRepository productRepository;

	public String createProduct(CreateProductRequestModel request)
			throws EmptyException, UserException, ProductException {
		AdminCreateProductRequestModel adminCreateProductRequestModel = new AdminCreateProductRequestModel();
		BeanUtils.copyProperties(request, adminCreateProductRequestModel);

		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
		if (Objects.isNull(sessionUserProfile)) {
			throw new UserException("此帳戶未登入，請重新確認");
		}

		return adminProductsService.createProduct(sessionUserProfile.getUserId(), adminCreateProductRequestModel);
	}

	public String updateProduct(String productId, UpdateProductRequestModel request)
			throws EmptyException, UserException, ProductException {
		AdminUpdateProductRequestModel adminUpdateProductRequestModel = new AdminUpdateProductRequestModel();
		BeanUtils.copyProperties(request, adminUpdateProductRequestModel);
		validateUserPermission(productId);
		return adminProductsService.updateProduct(productId, adminUpdateProductRequestModel);
	}

	public String deleteProduct(String productId) throws UserException, ProductException {
		validateUserPermission(productId);
		return adminProductsService.deleteProduct(productId);
	}

	public void validateUserPermission(String productId) throws UserException {
		UserProfile sessionUserProfile = (UserProfile) session.getAttribute("userProfile");
		if (Objects.isNull(sessionUserProfile)) {
			throw new UserException("此帳戶未登入，請重新確認");
		}

		UserProfile userProfile = productRepository.findByProductId(productId).getUserProfile();
		if (Objects.isNull(userProfile) || !sessionUserProfile.equals(userProfile)
				|| !StringUtils.equals(sessionUserProfile.getPassword(), userProfile.getPassword())) {
			throw new UserException("此帳戶不存在，請重新確認");
		}
	}
}
