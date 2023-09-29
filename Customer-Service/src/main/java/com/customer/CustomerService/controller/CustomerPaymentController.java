package com.customer.CustomerService.controller;

import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.service.CustomerService;
import com.payment.PaymentService.dtos.ResponsePaymentDto;
import com.payment.PaymentService.exceptions.PaymentsNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerPaymentController {

    private CustomerService customerService;
    @Autowired
    public CustomerPaymentController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/customer/payments")
    public List<ResponsePaymentDto>  getAllPayments(@RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException, PaymentsNotFound {

        return customerService.getAllPayments(userName);
    }
}
