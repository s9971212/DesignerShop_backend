package com.designershop.products;

import com.designershop.entities.ProductBrand;
import com.designershop.entities.ProductCategory;
import com.designershop.exceptions.ProductException;
import com.designershop.repositories.ProductBrandRepository;
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
public class ProductBrandService {

    private final ProductBrandRepository productBrandRepository;

    public ProductBrand readProductBrand(String brand) {
        ProductBrand productBrand = productBrandRepository.findByBrandIgnoreCase(brand);
        if (Objects.isNull(productBrand)) {
            productBrand = new ProductBrand();
            productBrand.setBrand(brand);
            productBrandRepository.save(productBrand);
        }

        return productBrand;
    }
}
