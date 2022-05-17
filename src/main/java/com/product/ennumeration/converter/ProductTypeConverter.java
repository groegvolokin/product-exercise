package com.product.ennumeration.converter;

import com.product.ennumeration.ProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {

    @Override
    public String convertToDatabaseColumn(ProductType productType) {
        return productType.convertToDBValue();
    }

    @Override
    public ProductType convertToEntityAttribute(String s) {
        return ProductType.of(s.toUpperCase());
    }
}
