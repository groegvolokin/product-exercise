package com.product.controller.dto.converter;

import com.product.controller.dto.response.ProductDTO;
import com.product.ennumeration.ProductType;
import com.product.model.Subscription;
import org.springframework.stereotype.Component;

import static com.product.ennumeration.ProductType.SUBSCRIPTION;

@Component
public class SubscriptionResponseConverter implements ProductConverter<Subscription> {

    @Override
    public ProductDTO convertToProductResponse(Subscription product) {
        ProductDTO.ProductDTOBuilder baseBuilder = getBaseBuilder(product);
        return baseBuilder
                .type(SUBSCRIPTION.name().toLowerCase())
                .properties(getProperties(product))
                .build();
    }

    @Override
    public ProductType getProductType() {
        return SUBSCRIPTION;
    }

    private String getProperties(Subscription subscription) {
        return "gb_limit".concat(":").concat(String.valueOf(subscription.getGbLimit()));
    }
}
