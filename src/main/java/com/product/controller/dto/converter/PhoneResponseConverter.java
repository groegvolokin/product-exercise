package com.product.controller.dto.converter;

import com.product.controller.dto.response.ProductDTO;
import com.product.ennumeration.ProductType;
import com.product.model.Phone;
import org.springframework.stereotype.Component;

import static com.product.ennumeration.ProductType.PHONE;

@Component
public class PhoneResponseConverter implements ProductConverter<Phone> {

    @Override
    public ProductDTO convertToProductResponse(Phone product) {
        ProductDTO.ProductDTOBuilder baseBuilder = getBaseBuilder(product);
        return baseBuilder
                .type(PHONE.name().toLowerCase())
                .properties(getProperties(product))
                .build();
    }

    @Override
    public ProductType getProductType() {
        return PHONE;
    }

    private String getProperties(Phone product) {
        return "color".concat(":").concat(product.getColor());
    }
}
