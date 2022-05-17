package com.product.service.impl;

import com.product.ennumeration.ProductType;
import com.product.model.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductLookupService;
import com.product.specification.DefaultProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultProductLookupServiceImpl implements ProductLookupService {

    private final DefaultProductSpecification productSpecification;
    private final ProductRepository productRepository;

    @Override
    public ProductType getProductType() {
        return ProductType.DEFAULT;
    }

    @Override
    public List<? extends Product> findAll(Map<String, String> queryParameters) {
        Specification<Product> specification = productSpecification.createSpecification(queryParameters);
        return productRepository.findAll(specification);
    }
}
