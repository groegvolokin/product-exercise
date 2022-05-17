package com.product.service;

import com.product.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> findAll();

    List<Product> findAll(Map<String, String> queryParameters);
}
