package com.product.util;

import com.product.ennumeration.ProductType;
import com.product.model.Phone;
import com.product.model.Subscription;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductUtil {

    public Phone createPhone(String color, String city, String store, Double price) {
        Phone phone = new Phone();
        phone.setColor(color);
        phone.setType(ProductType.PHONE);
        phone.setCity(city);
        phone.setPrice(price);
        phone.setStore(store);
        return phone;
    }

    public Subscription createSubscription(String city, String store, Integer limit, Double price) {
        Subscription subscription = new Subscription();
        subscription.setGbLimit(limit);
        subscription.setCity(city);
        subscription.setPrice(price);
        subscription.setStore(store);
        subscription.setType(ProductType.SUBSCRIPTION);
        return subscription;
    }
}
