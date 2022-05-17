package com.product.configuration;

import com.product.ennumeration.ProductType;
import com.product.model.Phone;
import com.product.model.Subscription;
import com.product.repository.PhoneRepository;
import com.product.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static com.product.ennumeration.ProductType.PHONE;
import static com.product.ennumeration.ProductType.SUBSCRIPTION;

@Slf4j
@Component
@Profile("!it")
@RequiredArgsConstructor
public class DBInitializer implements ApplicationRunner {
    private final PhoneRepository phoneRepository;
    private final SubscriptionRepository subscriptionRepository;
    private static final String DATA_PATH = "/data/README_data.csv";
    private static final String COMMA_DELIMITER = ",";

    Set<Subscription> subscriptions = new HashSet<>();
    Set<Phone> phones = new HashSet<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        readFromCSVFile();
        subscriptionRepository.saveAll(subscriptions);
        phoneRepository.saveAll(phones);
    }

    private void readFromCSVFile() {
        try (InputStream inputStream = getClass().getResourceAsStream(DATA_PATH)) {
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            boolean firstRow = true;
            String row;
            while ((row = reader.readLine()) != null) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }

                String[] cols = row.split(COMMA_DELIMITER);
                createProductFromRow(cols);
            }
        } catch (IOException e) {
            log.error("Error processing products: {}", e.getMessage());
            throw new RuntimeException("Exception while processing products");
        }

    }

    private void createProductFromRow(String[] cols) {
        ProductType productType = ProductType.of(cols[0].toUpperCase());
        if (SUBSCRIPTION.equals(productType)) {
            subscriptions.add(createSubscription(cols));
        } else {
            phones.add(createPhone(cols));
        }
    }

    private Phone createPhone(String[] cols) {
        String propertyValue = cols[1].split(":")[1];
        Phone product = new Phone();
        product.setPrice(Double.valueOf(cols[2]));
        product.setType(PHONE);
        product.setColor(propertyValue);
        product.setCity(cols[4].replace("\"", "").strip());
        product.setStore(cols[3].replace("\"", "").strip());
        return product;
    }

    private Subscription createSubscription(String[] cols) {
        String propertyValue = cols[1].split(":")[1];
        Subscription product = new Subscription();
        product.setType(SUBSCRIPTION);
        product.setGbLimit(Integer.valueOf(propertyValue));
        product.setPrice(Double.valueOf(cols[2]));
        product.setCity(cols[4].replace("\"", "").strip());
        product.setStore(cols[3].replace("\"", "").strip());
        return product;
    }
}
