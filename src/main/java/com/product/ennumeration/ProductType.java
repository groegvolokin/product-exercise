package com.product.ennumeration;

import com.product.exception.UnsupportedProductTypeException;

public enum ProductType {
    PHONE,
    SUBSCRIPTION,
    DEFAULT;

    public String convertToDBValue() {
        return this.name().toLowerCase();
    }

    public static ProductType of(String productType) {
        try {
            return ProductType.valueOf(productType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedProductTypeException("Please provide supported product type");
        }
    }
}
