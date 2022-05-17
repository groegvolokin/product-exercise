package com.product.service;

import com.product.ennumeration.ProductType;
import com.product.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductLookupService {

    ProductType getProductType();

    List<? extends Product> findAll(Map<String, String> queryParameters);
}
