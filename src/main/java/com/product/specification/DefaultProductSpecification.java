package com.product.specification;

import com.product.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class DefaultProductSpecification implements ProductSpecificationCreator {

    @Override
    public Specification<Product> createSpecification(Map<String, String> queryParameters) {
        return createSpecification(Collections.emptyMap(), Collections.emptyMap(), Collections.emptySet());
    }
}
