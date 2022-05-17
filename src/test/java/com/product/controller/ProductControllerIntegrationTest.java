package com.product.controller;

import com.product.BaseIntegrationTest;
import com.product.model.Phone;
import com.product.model.Subscription;
import com.product.repository.PhoneRepository;
import com.product.repository.ProductRepository;
import com.product.repository.SubscriptionRepository;
import com.product.util.ProductUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    private void setup() {
        phoneRepository.deleteAll();
        subscriptionRepository.deleteAll();
    }

    @Test
    void givenProducts_whenGetAllProductsNoParameters_thenStatus200AndReturnAll() throws Exception {
        //given
        Subscription subscription = ProductUtil.createSubscription("Gothenborg", "Hex", 100, 40D);
        Phone phone = ProductUtil.createPhone("blue", "Stockholm", "Lidl", 180D);
        subscriptionRepository.save(subscription);
        phoneRepository.save(phone);

        mockMvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))));
    }

    @Test
    void givenProducts_whenGetAllPhones_thenStatus200AndReturnPhonesOnly() throws Exception {
        //given
        String city = "Gothenborg";
        String color = "blue";
        String store = "Lidl";
        double price = 180D;
        Subscription subscription = ProductUtil.createSubscription("Gothenborg", "Hex", 100, 40D);
        Phone phone = ProductUtil.createPhone(color, city, store, price);
        subscriptionRepository.save(subscription);
        phoneRepository.save(phone);

        mockMvc.perform(get("/product").param("type", "phone").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.data[0].type", is("phone")))
                .andExpect(jsonPath("$.data[0].store_address", is(store.concat(", ").concat(city))))
                .andExpect(jsonPath("$.data[0].properties", is("color".concat(":").concat(color))))
                .andExpect(jsonPath("$.data[0].price", is(price)));
    }

    @Test
    void givenProducts_whenGetSubscriptionsBetween100And130_thenStatus200AndReturnTwo() throws Exception {
        //given
        Subscription subscription = ProductUtil.createSubscription("Gothenborg", "Hex", 100, 40D);
        Subscription subscription1 = ProductUtil.createSubscription("Stockholm", "Lidl", 130, 44D);
        Subscription subscription3 = ProductUtil.createSubscription("Malmo", "Knox", 150, 49D);
        subscriptionRepository.saveAll(List.of(subscription, subscription1, subscription3));

        mockMvc.perform(get("/product")
                        .param("type", "subscription")
                        .param("property:gb_limit_min", "100")
                        .param("property:gb_limit_max", "130")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))));
    }

    @Test
    void givenProducts_whenGetAllProductsWrongProductType_thenStatusIs400AndErrorMessage() throws Exception {
        //given
        mockMvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON).param("type", "random"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Please provide supported product type")));
    }
}
