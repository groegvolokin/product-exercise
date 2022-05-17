package com.product.specification;

import com.product.ennumeration.ProductType;
import com.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.BiFunction;

public interface ProductSpecificationCreator {

    Specification<? extends Product> createSpecification(Map<String, String> queryParameters);

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

    default <T extends Product> Specification<T> createSpecification(Map<String, BiFunction<String, Object, Specification<T>>> additionalFunctions,
                                                                     Map<String, String> queryParameters,
                                                                     Set<String> additionalParameters) {
        Set<String> allowedParameters = new HashSet<>(Arrays.asList("city", "max_price", "min_price"));
        allowedParameters.addAll(additionalParameters);
        Map<String, BiFunction<String, Object, Specification<T>>> propertyFunction = getBaseFunction();
        propertyFunction.putAll(additionalFunctions);
        return queryParameters.entrySet().stream()
                .filter(param -> allowedParameters.contains(param.getKey()))
                .map(entry -> propertyFunction.get(entry.getKey()).apply(entry.getKey(), entry.getValue()))
                .reduce(Specification::and).orElse(null);
    }

    default <T extends Product> Map<String, BiFunction<String, Object, Specification<T>>> getBaseFunction() {

        Map<String, BiFunction<String, Object, Specification<T>>> propertyToFunction = new HashMap<>();
        propertyToFunction.put("city", createCityFunction());
        propertyToFunction.put("max_price", createMaxPriceFunction());
        propertyToFunction.put("min_price", createMinPriceFunction());
        propertyToFunction.put("type", createTypeSpecification());
        return propertyToFunction;
    }
}
