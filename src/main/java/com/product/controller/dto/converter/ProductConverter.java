package com.product.controller.dto.converter;

import com.product.controller.dto.response.ProductDTO;
import com.product.ennumeration.ProductType;
import com.product.model.Product;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface ProductConverter<M extends Product> {

    ProductDTO convertToProductResponse(M product);

    ProductType getProductType();

    default List<ProductDTO> convertToProductResponseList(Collection<M> products) {
        return Objects.nonNull(products) ? products.stream().map(this::convertToProductResponse).toList() : Collections.emptyList();
    }

    default ProductDTO.ProductDTOBuilder getBaseBuilder(M product) {
        return ProductDTO.builder()
                .price(product.getPrice())
                .storeAddress(product.getStore().concat(", ").concat(product.getCity()));
    }
}
