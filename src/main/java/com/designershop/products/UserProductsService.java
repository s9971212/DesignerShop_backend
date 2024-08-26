package com.designershop.products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.designershop.admin.products.models.AdminCreateProductRequestModel;
import com.designershop.entities.*;
import com.designershop.exceptions.EmptyException;
import com.designershop.products.models.CreateProductEvaluationRequestModel;
import com.designershop.utils.DateTimeFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.designershop.exceptions.ProductException;
import com.designershop.repositories.ProductLikesRepository;
import com.designershop.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProductsService {

    private final HttpSession session;
    private final ProductRepository productRepository;
    private final ProductLikesRepository productLikesRepository;

    private final BigDecimal lowerStars = new BigDecimal("0.5");
    private final BigDecimal upperStars = new BigDecimal("5.0");

    public String readProductLikes(String productId) throws ProductException {
        Product product = productRepository.findByProductId(productId);
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        ProductLikes productLikes = productLikesRepository.findByUserIdAndProductId(userProfile.getUserId(), productId);
        String response = "N";
        if (Objects.nonNull(productLikes)) {
            response = "Y";
        }

        return response;
    }

    @Transactional
    public String updateProductLikes(String productId) throws ProductException {
        Product product = productRepository.findByProductId(productId);
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        ProductLikes productLikes = productLikesRepository.findByUserIdAndProductId(userProfile.getUserId(), productId);
        if (Objects.isNull(productLikes)) {
            ProductLikesId productLikesId = new ProductLikesId();
            productLikesId.setUserId(userProfile.getUserId());
            productLikesId.setProductId(product.getProductId());

            productLikes = new ProductLikes();
            productLikes.setId(productLikesId);
            productLikesRepository.save(productLikes);
        } else {
            productLikesRepository.delete(productLikes);
        }

        long likes = productLikesRepository.findCountByProductId(productId);
        product.setLikes((int) likes);
        productRepository.save(product);

        return product.getProductName();
    }
}
