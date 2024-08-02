package com.customer.CustomerService.controller;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.customer.CustomerService.dtos.RequestAddressCustDto;
import com.customer.CustomerService.dtos.order.ResponseCustOrderDto;
import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.exceptions.CustomerDetailsNotAvailable;
import com.customer.CustomerService.service.CustomerService;
import com.identityservice.service.security.JwtService;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.orders.OrdersService.dtos.ResponseOrderDto;
import com.orders.OrdersService.dtos.customer.ResponseOrderCustomerDateDto;
import com.orders.OrdersService.dtos.customer.ResponseOrdersCustomerTotalDto;
import com.orders.OrdersService.exceptions.OrdersNotPlacedException;
import com.orders.OrdersService.exceptions.PaymentFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://localhost:5173/")
@RestController
public class CustomerOrderController {


    private CustomerService customerService;
    @Autowired
    public CustomerOrderController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer/order")
    public String order(@RequestHeader("LoggedInUser") String userName,@RequestBody RequestAddressCustDto addressCustDto) throws AddressNotFoundWithEmail, CartServiceUpdationException, CartDetailsNotFound, OrdersNotPlacedException, PaymentFailedException {

        return customerService.order(userName,addressCustDto);
    }
    @GetMapping("/customer/orders")
    public List<ResponseOrderDto> getAllOrdersByCustomerEmail(@RequestHeader("LoggedInUser") String userName) throws OrdersNotPlacedException, CustomerDetailsNotAvailable {

        return customerService.getAllOrdersByCustomerEmail(userName);
    }

    // Total Amount per Shop
    @GetMapping("/customer/orders/totalAmountPerShop")
    public List<ResponseOrdersCustomerTotalDto> getCustomersTotalAmount(@RequestHeader("LoggedInUser") String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException {

        return customerService.getCustomersTotalAmount(userName);
    }

    //OrderedOn particular Date
    @GetMapping("/customer/orders/orderedOn/total")
    public List<ResponseOrderCustomerDateDto> getCustomersTotalAndDate(@RequestHeader("LoggedInUser") String userName) throws CustomerDetailsNotAvailable, OrdersNotPlacedException {

        return customerService.getCustomersTotalAndDate(userName);
    }
}
