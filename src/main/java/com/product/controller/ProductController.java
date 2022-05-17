package com.product.controller;

import com.product.controller.dto.converter.ProductResponseConverter;
import com.product.controller.dto.response.ProductResponse;
import com.product.model.Product;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productLookupService;
    private final ProductResponseConverter productResponseConverter;

    @GetMapping
    public ResponseEntity<ProductResponse> getProducts(@RequestParam Map<String, String> requestParams) {
        List<Product> productList = productLookupService.findAll(requestParams);
        ProductResponse productResponse = productResponseConverter.createResponse(productList);
        return ResponseEntity.ok().body(productResponse);
    }
}
