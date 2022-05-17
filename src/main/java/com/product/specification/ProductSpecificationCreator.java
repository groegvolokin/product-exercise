package com.product.specification;

import com.product.ennumeration.ProductType;
import com.product.model.Phone;
import com.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public interface ProductSpecificationCreator {

    Specification<? extends Product> createSpecification(Map<String, String> queryParameters);

    default Specification<? extends Product> createSpecification(Map<String, String> queryParameters, Map<String,
            BiFunction<String, Object, Specification<Phone>>> propertyToFunction,
                                                                 Set<String> allowedParameters) {
        return queryParameters.entrySet().stream()
                .filter(param -> allowedParameters.contains(param.getKey()))
                .map(entry -> propertyToFunction.get(entry.getKey()).apply(entry.getKey(), entry.getValue()))
                .reduce(Specification::and).orElse(null);
    }

    default <T extends Product> BiFunction<String, Object, Specification<T>> createCityFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("city"), String.valueOf(value));
    }

    default <T extends Product> BiFunction<String, Object, Specification<T>> createMaxPriceFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), Integer.valueOf(String.valueOf(value)));
    }

    default <T extends Product> BiFunction<String, Object, Specification<T>> createMinPriceFunction() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.valueOf(String.valueOf(value)));
    }

    default <T extends Product> BiFunction<String, Object, Specification<T>> createTypeSpecification() {
        return (key, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("type"), ProductType.valueOf(String.valueOf(value).toUpperCase()));
    }
}
