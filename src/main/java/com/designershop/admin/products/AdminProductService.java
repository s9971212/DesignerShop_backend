package com.designershop.admin.products;

import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.admin.products.models.AdminReadProductResponseModel;
import com.designershop.admin.products.models.AdminUpdateProductRequestModel;
import com.designershop.entities.*;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.products.ProductBrandService;
import com.designershop.products.ProductCategoryService;
import com.designershop.repositories.ProductImageRepository;
import com.designershop.repositories.ProductRepository;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.utils.DateTimeFormatUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final HttpSession session;
    private final ProductCategoryService productCategoryService;
    private final ProductBrandService productBrandService;
    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createProduct(String userId, AdminCreateProductRequestModel request) throws EmptyException, ProductException {
        String categoryId = request.getCategoryId();
        String brand = request.getBrand();
        String productName = request.getName();
        String productDescription = request.getDescription();
        String priceString = request.getPrice();
        String stockQuantityString = request.getStockQuantity();
        List<String> images = request.getImages();
        String termsCheckBox = request.getTermsCheckBox();

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(categoryId) || StringUtils.isBlank(brand) || StringUtils.isBlank(productName)
                || StringUtils.isBlank(priceString) || StringUtils.isBlank(stockQuantityString) || StringUtils.isBlank(termsCheckBox)) {
            throw new EmptyException("商品類別、品牌、商品名稱、價格、庫存數量與條款確認不得為空");
        }
        if (images.isEmpty()) {
            throw new EmptyException("商品圖片至少須有一張");
        }
        // 因應綠界價格只能正整數修改正規表示式，允許小數點可使用\d+(\.\d+)?
        if (!priceString.matches("\\d+")) {
            throw new ProductException("價格只能有數字，請重新確認");
        }
        if (!stockQuantityString.matches("\\d+")) {
            throw new ProductException("庫存數量只能有數字，請重新確認");
        }

        ProductCategory productCategory = productCategoryService.readProductCategory(categoryId);
        ProductBrand productBrand = productBrandService.readProductBrand(brand);
        BigDecimal price = new BigDecimal(priceString);
        int stockQuantity = Integer.parseInt(stockQuantityString);
        LocalDateTime currentDateTime = DateTimeFormatUtil.currentDateTime();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        String updatedUser = userProfile.getUserId();
        if (!StringUtils.equals(updatedUser, userId)) {
            userProfile = userProfileRepository.findByUserId(userId);
        }

        Product productCreate = new Product();
        productCreate.setName(productName);
        productCreate.setDescription(productDescription);
        productCreate.setPrice(price);
        productCreate.setOriginalPrice(price);
        productCreate.setStockQuantity(stockQuantity);
        productCreate.setCreatedDate(currentDateTime);
        productCreate.setUpdatedUser(updatedUser);
        productCreate.setUpdatedDate(currentDateTime);
        productCreate.setUserId(userProfile.getUserId());
        productCreate.setProductCategory(productCategory);
        productCreate.setProductBrand(productBrand);
        productRepository.save(productCreate);

        for (String image : images) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(image);
            productImage.setProduct(productCreate);
            productImageRepository.save(productImage);
        }

        return productName;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<AdminReadProductResponseModel> readAllProductByUser(String userId) throws ProductException {
        List<AdminReadProductResponseModel> response = new ArrayList<>();

        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (Objects.isNull(userProfile)) {
            throw new ProductException("此帳戶不存在，請重新確認");
        }

        List<Product> productList = productRepository.findAllByUserId(userId);
        for (Product product : productList) {
            AdminReadProductResponseModel adminReadProductResponseModel = new AdminReadProductResponseModel();
            BeanUtils.copyProperties(product, adminReadProductResponseModel);
            adminReadProductResponseModel.setProductId(Integer.toString(product.getProductId()));
            adminReadProductResponseModel.setCategory(product.getProductCategory().getCategory());
            adminReadProductResponseModel.setBrand(product.getProductBrand().getBrand());
            adminReadProductResponseModel.setPrice(product.getPrice().toString());
            adminReadProductResponseModel.setOriginalPrice(product.getOriginalPrice().toString());
            adminReadProductResponseModel.setStockQuantity(Integer.toString(product.getStockQuantity()));
            adminReadProductResponseModel.setSoldQuantity(Integer.toString(product.getSoldQuantity()));
            adminReadProductResponseModel.setLikes(Integer.toString(product.getLikes()));
            List<String> images = new ArrayList<>();
            for (ProductImage productImage : product.getProductImages()) {
                images.add(productImage.getImage());
            }
            adminReadProductResponseModel.setImages(images);
            adminReadProductResponseModel.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(product.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
            adminReadProductResponseModel.setIsDeleted(product.isDeleted() ? "Y" : "N");
            response.add(adminReadProductResponseModel);
        }

        return response;
    }

    public AdminReadProductResponseModel readProduct(String productId) throws ProductException {
        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        AdminReadProductResponseModel response = new AdminReadProductResponseModel();
        BeanUtils.copyProperties(product, response);
        response.setProductId(Integer.toString(product.getProductId()));
        response.setCategory(product.getProductCategory().getCategory());
        response.setBrand(product.getProductBrand().getBrand());
        response.setPrice(product.getPrice().toString());
        response.setOriginalPrice(product.getOriginalPrice().toString());
        response.setStockQuantity(Integer.toString(product.getStockQuantity()));
        response.setSoldQuantity(Integer.toString(product.getSoldQuantity()));
        response.setLikes(Integer.toString(product.getLikes()));
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : product.getProductImages()) {
            images.add(productImage.getImage());
        }
        response.setImages(images);
        response.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(product.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
        response.setIsDeleted(product.isDeleted() ? "Y" : "N");
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateProduct(String productId, AdminUpdateProductRequestModel request) throws EmptyException, ProductException {
        String categoryId = request.getCategoryId();
        String brand = request.getBrand();
        String name = request.getName();
        String description = request.getDescription();
        String priceString = request.getPrice();
        String stockQuantityString = request.getStockQuantity();
        String isDeletedString = request.getIsDeleted();
        List<String> images = request.getImages();
        String termsCheckBox = request.getTermsCheckBox();

        if (StringUtils.isBlank(productId) || StringUtils.isBlank(categoryId) || StringUtils.isBlank(brand) || StringUtils.isBlank(name)
                || StringUtils.isBlank(priceString) || StringUtils.isBlank(stockQuantityString) || StringUtils.isBlank(isDeletedString)
                || StringUtils.isBlank(termsCheckBox)) {
            throw new EmptyException("商品類別、品牌、商品名稱、價格、庫存數量與條款確認不得為空");
        }
        if (images.isEmpty()) {
            throw new EmptyException("商品圖片至少須有一張");
        }
        // 因應綠界價格只能正整數修改正規表示式，允許小數點可使用\d+(\.\d+)?
        if (!priceString.matches("\\d+")) {
            throw new ProductException("價格只能有數字，請重新確認");
        }
        if (!stockQuantityString.matches("\\d+")) {
            throw new ProductException("庫存數量只能有數字，請重新確認");
        }
        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        ProductCategory productCategory = productCategoryService.readProductCategory(categoryId);
        ProductBrand productBrand = productBrandService.readProductBrand(brand);
        BigDecimal price = new BigDecimal(priceString);
        int priceCompare = product.getOriginalPrice().compareTo(price);
        int stockQuantity = Integer.parseInt(stockQuantityString);
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        boolean isDeleted = StringUtils.equals("Y", isDeletedString);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        if (priceCompare < 0) {
            product.setOriginalPrice(price);
        }
        product.setStockQuantity(stockQuantity);
        product.setUpdatedUser(userProfile.getUserId());
        product.setUpdatedDate(DateTimeFormatUtil.currentDateTime());
        product.setDeleted(isDeleted);
        product.setProductCategory(productCategory);
        product.setProductBrand(productBrand);
        productRepository.save(product);

        Set<String> productImages = productImageRepository.findAllByProductId(Integer.parseInt(productId)).stream().map(ProductImage::getImage)
                .collect(Collectors.toSet());
        for (String image : images) {
            if (!productImages.contains(image)) {
                ProductImage productImage = new ProductImage();
                productImage.setImage(image);
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }

        return name;
    }

    public String deleteProduct(String productId) throws ProductException {
        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        product.setUpdatedUser(userProfile.getUserId());
        product.setUpdatedDate(DateTimeFormatUtil.currentDateTime());
        product.setDeleted(true);
        productRepository.save(product);
        return product.getName();
    }
}
