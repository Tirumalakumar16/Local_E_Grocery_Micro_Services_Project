package com.customer.CustomerService.controller;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.customer.CustomerService.dtos.RequestAddressCustDto;
import com.customer.CustomerService.dtos.order.ResponseCustOrderDto;
import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.service.CustomerService;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerOrderController {


    private CustomerService customerService;
    @Autowired
    public CustomerOrderController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer/order")
    public String order(@RequestHeader("LoggedInUser") String userName,@RequestBody RequestAddressCustDto addressCustDto) throws AddressNotFoundWithEmail, CartServiceUpdationException, CartDetailsNotFound {

        return customerService.order(userName,addressCustDto);
    }
    @GetMapping("/customer/orders")
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(@RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException, OrdersNotPlacedException {

        return customerService.getAllOrdersByCustomerEmail(userName);
    }
}
