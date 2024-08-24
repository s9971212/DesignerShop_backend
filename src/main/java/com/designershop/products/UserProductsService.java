package com.designershop.products;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.designershop.entities.Product;
import com.designershop.entities.ProductLikes;
import com.designershop.entities.ProductLikesId;
import com.designershop.entities.UserProfile;
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

    @Transactional
    public String updateProductLikes(String productId) throws ProductException {
        Product product = productRepository.findByProductId(productId);
        if (Objects.isNull(product)) {
            throw new ProductException("此商品不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        ProductLikes productLikes = productLikesRepository.findByUserIdAndProductId(userProfile.getUserId(), productId);
        long likes = productLikesRepository.findCountByProductId(productId);
        if (Objects.isNull(productLikes)) {
            product.setLikes((int) ++likes);
            productRepository.save(product);

            ProductLikesId productLikesId = new ProductLikesId();
            productLikesId.setUserId(userProfile.getUserId());
            productLikesId.setProductId(product.getProductId());

            productLikes = new ProductLikes();
            productLikes.setId(productLikesId);
            productLikes.setProduct(product);
            productLikesRepository.save(productLikes);
        } else {
            product.setLikes((int) --likes);
            productRepository.save(product);

            productLikesRepository.delete(productLikes);
        }

        return product.getProductName();
    }
}
