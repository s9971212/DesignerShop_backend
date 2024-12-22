package com.designershop.products;

import com.designershop.admin.products.AdminProductService;
import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.entities.Product;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.CreateProductRequestModel;
import com.designershop.products.models.UpdateProductRequestModel;
import com.designershop.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class SellerProductService {

    private final HttpSession session;
    private final AdminProductService adminProductService;
    private final ProductRepository productRepository;

    public String createProduct(CreateProductRequestModel request) throws EmptyException,  ProductException {
        AdminCreateProductRequestModel adminCreateProductRequestModel = new AdminCreateProductRequestModel();
        BeanUtils.copyProperties(request, adminCreateProductRequestModel);

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new ProductException("此帳戶未登入，請重新確認");
        }

        return adminProductService.createProduct(userProfile.getUserId(), adminCreateProductRequestModel);
    }

    public String updateProduct(String productId, UpdateProductRequestModel request) throws EmptyException, ProductException {
        AdminUpdateProductRequestModel adminUpdateProductRequestModel = new AdminUpdateProductRequestModel();
        BeanUtils.copyProperties(request, adminUpdateProductRequestModel);
        validateProductPermission(productId);
        adminUpdateProductRequestModel.setIsDeleted("N");
        return adminProductService.updateProduct(productId, adminUpdateProductRequestModel);
    }

    public String deleteProduct(String productId) throws ProductException {
        validateProductPermission(productId);
        return adminProductService.deleteProduct(productId);
    }

    public void validateProductPermission(String productId) throws ProductException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new ProductException("此帳戶未登入，請重新確認");
        }

        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (!StringUtils.equals(userProfile.getUserId(), product.getUserId())) {
            throw new ProductException("此帳戶不存在，請重新確認");
        }

        if (product.isDeleted()) {
            throw new ProductException("此商品已被刪除，請重新確認");
        }
    }
}