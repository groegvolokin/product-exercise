package com.product.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {
    private String type;
    private String properties;
    private Double price;

    @JsonProperty(value = "store_address")
    private String storeAddress;
}
