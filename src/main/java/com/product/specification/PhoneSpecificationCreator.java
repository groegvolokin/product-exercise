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

    private static final Set<String> ALLOWED_PARAMETERS = Set.of("property:color", "city", "max_price", "min_price");

    private Map<String, BiFunction<String, Object, Specification<Phone>>> propertyToFunction;

    @PostConstruct
    private void init() {
        propertyToFunction = new HashMap<>();
        propertyToFunction.put("property:color", createColorFunction());
        propertyToFunction.put("city", createCityFunction());
        propertyToFunction.put("max_price", createMaxPriceFunction());
        propertyToFunction.put("min_price", createMinPriceFunction());
        propertyToFunction.put("type", createTypeSpecification());
    }

    @Override
    public Specification<Phone> createSpecification(Map<String, String> queryParameters) {
        return queryParameters.entrySet().stream()
                .filter(param -> ALLOWED_PARAMETERS.contains(param.getKey()))
                .map(entry -> propertyToFunction.get(entry.getKey()).apply(entry.getKey(), entry.getValue()))
                .reduce(Specification::and).orElse(null);
    }

    private BiFunction<String, Object, Specification<Phone>> createColorFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("color"), String.valueOf(value));
    }
}
