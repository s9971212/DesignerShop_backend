package com.designershop.products;

import com.designershop.entities.ProductCategory;
import com.designershop.exceptions.ProductException;
import com.designershop.repositories.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategory readProductCategory(String categoryId) throws ProductException {
        ProductCategory productCategory = productCategoryRepository.findByCategoryId(Integer.parseInt(categoryId));
        if (Objects.isNull(productCategory)) {
            throw new ProductException("此商品類別不存在，請重新確認");
        }

        return productCategory;
    }
}
