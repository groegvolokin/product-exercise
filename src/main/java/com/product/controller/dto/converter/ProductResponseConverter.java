package com.product.controller.dto.converter;

import com.product.controller.dto.converter.factory.ProductResponseConverterFactory;
import com.product.controller.dto.response.ProductDTO;
import com.product.controller.dto.response.ProductResponse;
import com.product.ennumeration.ProductType;
import com.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductResponseConverter {

    private final ProductResponseConverterFactory converterFactory;

    public ProductResponse createResponse(Collection<Product> products) {
        if (products == null || products.isEmpty()) {
            return ProductResponse.builder().data(Collections.emptyList()).build();
        }

        Map<ProductType, List<Product>> productTypeToProducts = products.stream().collect(Collectors.groupingBy(Product::getType));
        List<ProductDTO> productDTOS = createProductDTOS(productTypeToProducts);
        return ProductResponse.builder().data(productDTOS).build();
    }

    private List<ProductDTO> createProductDTOS(Map<ProductType, List<Product>> productTypeToProducts) {
        return productTypeToProducts.keySet().stream().map(products -> {
            ProductConverter<Product> productConverter = converterFactory.getProductConverter(products);
            return productConverter.convertToProductResponseList(productTypeToProducts.get(products));
        }).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
