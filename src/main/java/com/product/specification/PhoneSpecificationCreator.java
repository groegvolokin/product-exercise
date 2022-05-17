package com.product.specification;

import com.product.model.Phone;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class PhoneSpecificationCreator implements ProductSpecificationCreator {

    private static final Set<String> ALLOWED_PARAMETERS = Set.of("property:color");

    private Map<String, BiFunction<String, Object, Specification<Phone>>> propertyToFunction;

    @PostConstruct
    private void init() {
        propertyToFunction = new HashMap<>();
        propertyToFunction.put("property:color", createColorFunction());
    }

    @Override
    public Specification<Phone> createSpecification(Map<String, String> queryParameters) {
        return createSpecification(propertyToFunction, queryParameters, ALLOWED_PARAMETERS);
    }

    private BiFunction<String, Object, Specification<Phone>> createColorFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("color"), String.valueOf(value));
    }
}
