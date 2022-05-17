package com.product.service;

import com.product.model.Subscription;
import com.product.repository.SubscriptionRepository;
import com.product.service.impl.SubscriptionLookupServiceImpl;
import com.product.specification.SubscriptionSpecificationCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionLookupServiceImplUnitTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionSpecificationCreator specificationCreator;

    @InjectMocks
    private SubscriptionLookupServiceImpl subscriptionLookupService;

    @Test
    void whenGivenRequestParameters_shouldRerunListOfSubscriptions() {
        //given
        List<Subscription> subscriptions = List.of(mock(Subscription.class));
        when(specificationCreator.createSpecification(anyMap())).thenReturn(mock(Specification.class));
        when(subscriptionRepository.findAll(any(Specification.class))).thenReturn(subscriptions);

        //when
        List<Subscription> result = subscriptionLookupService.findAll(Map.of("type", "subscription"));

        //assert
        assertNotNull(result);
        assertEquals(subscriptions, result);
    }
}
