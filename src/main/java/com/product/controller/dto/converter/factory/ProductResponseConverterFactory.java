package com.product.controller.dto.converter.factory;

import com.product.controller.dto.converter.ProductConverter;
import com.product.ennumeration.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductResponseConverterFactory {

    private Map<ProductType, ProductConverter> productTypeToConverter;
    private final List<ProductConverter> productConverters;

    @PostConstruct
    private void init() {
        productTypeToConverter = productConverters.stream().collect(Collectors.toMap(ProductConverter::getProductType, Function.identity()));
    }

    public ProductConverter getProductConverter(ProductType productType) {
        return productTypeToConverter.get(productType);
    }

}
