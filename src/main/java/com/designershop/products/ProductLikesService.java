package com.designershop.products;

import com.designershop.entities.Product;
import com.designershop.entities.ProductLikes;
import com.designershop.entities.ProductLikesId;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.ProductLikesRepository;
import com.designershop.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductLikesService {

    private final HttpSession session;
    private final ProductRepository productRepository;
    private final ProductLikesRepository productLikesRepository;

    private final BigDecimal lowerStars = new BigDecimal("0.5");
    private final BigDecimal upperStars = new BigDecimal("5.0");

    public String readProductLikes(String productId) throws ProductException {
        validateProductPermission(productId);

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            return "N";
        }

        ProductLikes productLikes = productLikesRepository.findByUserIdAndProductId(userProfile.getUserId(), Integer.parseInt(productId));
        return Objects.nonNull(productLikes) ? "Y" : "N";
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateProductLikes(String productId) throws  ProductException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new ProductException("此帳戶未登入，請重新確認");
        }

        Product product = validateProductPermission(productId);

        ProductLikes productLikes = productLikesRepository.findByUserIdAndProductId(userProfile.getUserId(), Integer.parseInt(productId));
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

        long likes = productLikesRepository.findCountByProductId(Integer.parseInt(productId));
        product.setLikes((int) likes);
        productRepository.save(product);

        return product.getName();
    }

    public Product validateProductPermission(String productId) throws ProductException {
        Product product = productRepository.findByProductId(Integer.parseInt(productId));
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        if (product.isDeleted()) {
            throw new ProductException("此商品已被刪除，請重新確認");
        }

        return product;
    }
}
