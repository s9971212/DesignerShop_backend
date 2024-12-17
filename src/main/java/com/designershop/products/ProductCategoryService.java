package com.designershop.products;

import com.designershop.entities.*;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.products.models.ReadProductResponseModel;
import com.designershop.repositories.ProductCategoryRepository;
import com.designershop.repositories.ProductRepository;
import com.designershop.repositories.UserProfileRepository;
import com.designershop.utils.DateTimeFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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
