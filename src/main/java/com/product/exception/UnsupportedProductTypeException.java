package com.product.exception;

public class UnsupportedProductTypeException extends RuntimeException{

    public UnsupportedProductTypeException(String message) {
        super(message);
    }
}
