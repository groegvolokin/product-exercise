package com.product.service.factory;

import com.product.ennumeration.ProductType;
import com.product.service.ProductLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductLookupFactory {
    private final List<ProductLookupService> productLookupImplementations;
    private Map<ProductType, ProductLookupService> productTypeToLookupService;

    @PostConstruct
    private void init() {
        productTypeToLookupService = productLookupImplementations.stream()
                .collect(Collectors.toMap(ProductLookupService::getProductType, Function.identity()));
    }

    public ProductLookupService getLookupService(String type) {
        if (type == null || type.isBlank()) {
            return productTypeToLookupService.get(ProductType.DEFAULT);
        }

        ProductType productType = ProductType.of(type.toUpperCase());
        return productTypeToLookupService.get(productType);
    }
}
