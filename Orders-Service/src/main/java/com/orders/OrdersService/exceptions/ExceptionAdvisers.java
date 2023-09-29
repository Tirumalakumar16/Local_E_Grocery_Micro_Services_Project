package com.orders.OrdersService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvisers {

    @ExceptionHandler(OrdersNotPlacedException.class)
    public ResponseEntity<ExceptionMessage> ordersException1(Exception exception){

        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setExceptionMessage(exception.getMessage());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
}
