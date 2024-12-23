package com.designershop.products;

import com.designershop.entities.Product;
import com.designershop.entities.ProductImage;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.ProductException;
import com.designershop.products.models.ReadProductResponseModel;
import com.designershop.repositories.ProductRepository;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.utils.DateTimeFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;

    // TODO 未來改成首頁顯示的商品
    //	public List<ReadProductRequestModel> readAllProduct() {
    //		List<ReadProductRequestModel> response = new ArrayList<>();
    //
    //		List<Product> productList = productRepository.findAll();
    //		for (Product product : productList) {
    //			ReadProductRequestModel readProductRequestModel = new ReadProductRequestModel();
    //			BeanUtils.copyProperties(product, readProductRequestModel);
    //			readProductRequestModel.setPrice(product.getPrice().toString());
    //			readProductRequestModel.setStockQuantity(Integer.toString(product.getStockQuantity()));
    //			readProductRequestModel.setSoldQuantity(Integer.toString(product.getSoldQuantity()));
    //			readProductRequestModel.setLikes(Integer.toString(product.getLikes()));
    //			readProductRequestModel.setProductCategory(product.getProductCategory().getCategoryName());
    //			readProductRequestModel.setProductBrand(product.getProductBrand().getBrand());
    //			response.add(readProductRequestModel);
    //		}
    //
    //		return response;
    //	}

    public List<ReadProductResponseModel> readAllProductByUser(String userId) throws ProductException {
        List<ReadProductResponseModel> response = new ArrayList<>();

        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (Objects.isNull(userProfile)) {
            throw new ProductException("此帳戶不存在，請重新確認");
        }

        if (userProfile.isDeleted()) {
            throw new ProductException("此帳戶已被刪除，請重新確認");
        }

        List<Product> productList = productRepository.findAllByUserId(userId);
        for (Product product : productList) {
            if (!product.isDeleted()) {
                ReadProductResponseModel readProductResponseModel = new ReadProductResponseModel();
                BeanUtils.copyProperties(product, readProductResponseModel);
                readProductResponseModel.setProductId(Integer.toString(product.getProductId()));
                readProductResponseModel.setCategory(product.getProductCategory().getCategory());
                readProductResponseModel.setBrand(product.getProductBrand().getBrand());
                readProductResponseModel.setPrice(product.getPrice().toString());
                readProductResponseModel.setOriginalPrice(product.getOriginalPrice().toString());
                readProductResponseModel.setStockQuantity(Integer.toString(product.getStockQuantity()));
                readProductResponseModel.setSoldQuantity(Integer.toString(product.getSoldQuantity()));
                readProductResponseModel.setLikes(Integer.toString(product.getLikes()));

                List<String> images = new ArrayList<>();
                for (ProductImage productImage : product.getProductImages()) {
                    images.add(productImage.getImage());
                }
                readProductResponseModel.setImages(images);
                readProductResponseModel.setCreatedDate(DateTimeFormatUtil.localDateTimeFormat(product.getCreatedDate(), DateTimeFormatUtil.FULL_DATE_DASH_TIME));
                response.add(readProductResponseModel);
            }
        }

        return response;
    }

    public ReadProductResponseModel readProduct(String productId) throws ProductException {
        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        if (product.isDeleted()) {
            throw new ProductException("此商品已被刪除，請重新確認");
        }

        ReadProductResponseModel response = new ReadProductResponseModel();
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

        return response;
    }
}
