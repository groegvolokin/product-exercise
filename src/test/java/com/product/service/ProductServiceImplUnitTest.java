package com.product.service;

import com.product.model.Product;
import com.product.model.Subscription;
import com.product.repository.ProductRepository;
import com.product.service.factory.ProductLookupFactory;
import com.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductLookupFactory productLookupFactory;

    @Mock
    private ProductLookupService productLookupService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void givenNoArguments_shouldReturnAllProducts() {
        //given
        List<Product> products = List.of(mock(Product.class));
        when(productRepository.findAll()).thenReturn(products);

        //when
        List<? extends Product> result = productService.findAll(Collections.emptyMap());

        //assert
        assertNotNull(result);
        assertEquals(products, result);
        verify(productLookupFactory, never()).getLookupService(anyString());
    }
}
