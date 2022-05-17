package com.product.service.impl;

import com.product.ennumeration.ProductType;
import com.product.model.Phone;
import com.product.repository.PhoneRepository;
import com.product.service.ProductLookupService;
import com.product.specification.PhoneSpecificationCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PhoneLookupServiceImpl implements ProductLookupService {

    private final PhoneSpecificationCreator phoneSpecificationCreator;
    private final PhoneRepository phoneRepository;

    @Override
    public ProductType getProductType() {
        return ProductType.PHONE;
    }

    @Override
    public List<Phone> findAll(Map<String, String> queryParameters) {
        Specification<Phone> phoneSpecification = phoneSpecificationCreator.createSpecification(queryParameters);
        return phoneRepository.findAll(phoneSpecification);
    }
}
