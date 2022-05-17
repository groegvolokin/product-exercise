package com.product.service.impl;

import com.product.model.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductLookupService;
import com.product.service.ProductService;
import com.product.service.factory.ProductLookupFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String TYPE = "type";
    private final ProductRepository productRepository;
    private final ProductLookupFactory productLookupFactory;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAll(Map<String, String> queryParameters) {
        if (queryParameters == null || queryParameters.isEmpty()) {
            return findAll();
        }

        ProductLookupService productLookupService = productLookupFactory.getLookupService(queryParameters.get(TYPE));
        return (List<Product>) productLookupService.findAll(queryParameters);
    }
}
