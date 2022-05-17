package com.product.model;

import com.product.ennumeration.ProductType;
import com.product.ennumeration.converter.ProductTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "store")
    private String store;

    @Column(name = "city")
    private String city;

    @Convert(converter = ProductTypeConverter.class)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private ProductType type;
}
