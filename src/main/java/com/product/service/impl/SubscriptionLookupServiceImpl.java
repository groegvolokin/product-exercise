package com.product.service.impl;

import com.product.ennumeration.ProductType;
import com.product.model.Subscription;
import com.product.repository.SubscriptionRepository;
import com.product.service.ProductLookupService;
import com.product.specification.SubscriptionSpecificationCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubscriptionLookupServiceImpl implements ProductLookupService {

    private final SubscriptionSpecificationCreator specificationCreator;
    private final SubscriptionRepository specificationRepository;

    @Override
    public ProductType getProductType() {
        return ProductType.SUBSCRIPTION;
    }

    @Override
    public List<Subscription> findAll(Map<String, String> queryParameters) {
        Specification<Subscription> specification = specificationCreator.createSpecification(queryParameters);
        return specificationRepository.findAll(specification);
    }
}
