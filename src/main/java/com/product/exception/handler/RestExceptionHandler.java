package com.product.exception.handler;

import com.product.exception.UnsupportedProductTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {UnsupportedProductTypeException.class})
    public ErrorMessage handUnsupportedProductTypeException(UnsupportedProductTypeException ex) {
        return ErrorMessage.builder().error(ex.getMessage()).build();
    }
}
