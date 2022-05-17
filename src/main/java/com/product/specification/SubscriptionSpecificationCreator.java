package com.product.specification;

import com.product.model.Subscription;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class SubscriptionSpecificationCreator implements ProductSpecificationCreator {

    private static final Set<String> ALLOWED_PARAMETERS = Set.of("property:gb_limit_min", "property:gb_limit_max");

    private Map<String, BiFunction<String, Object, Specification<Subscription>>> propertyToFunction;

    @PostConstruct
    private void init() {
        propertyToFunction = new HashMap<>();
        propertyToFunction.put("property:gb_limit_min", createMinLimitFunction());
        propertyToFunction.put("property:gb_limit_max", createMaxLimitFunction());
    }

    @Override
    public Specification<Subscription> createSpecification(Map<String, String> queryParameters) {
        return createSpecification(propertyToFunction, queryParameters, ALLOWED_PARAMETERS);
    }

    private BiFunction<String, Object, Specification<Subscription>> createMaxLimitFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("gbLimit"), Integer.valueOf(String.valueOf(value)));
    }

    private BiFunction<String, Object, Specification<Subscription>> createMinLimitFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("gbLimit"), Integer.valueOf(String.valueOf(value)));
    }
}
