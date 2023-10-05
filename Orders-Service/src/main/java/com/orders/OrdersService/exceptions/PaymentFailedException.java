package com.orders.OrdersService.exceptions;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String pleaseTryAgain) {
        super(pleaseTryAgain);
    }
}
