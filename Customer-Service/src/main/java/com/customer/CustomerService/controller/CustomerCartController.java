package com.customer.CustomerService.controller;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.customer.CustomerService.dtos.RequestCustomerDto;
import com.customer.CustomerService.dtos.cart.RequestCustCartDto;
import com.customer.CustomerService.exceptions.CartDetailsNotFoundException;
import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.service.CustomerService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerCartController {

    private CustomerService customerService;
    @Autowired
    public CustomerCartController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer/cart")
    public ResponseEntity<ResponseCartDto> saveCart(@RequestBody RequestCustCartDto requestCustCartDto, @RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException {

        return customerService.saveCart(requestCustCartDto,userName);
    }
    @PutMapping("/customer/cart")
    public ResponseEntity<ResponseCartDto> updateCart(@RequestBody UpdateCartDto updateCartDto,@RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException {
        return customerService.updatecart(updateCartDto,userName);
    }
    @GetMapping("/customer/carts")
    public ResponseEntity<List<ResponseCartDto>> getAllCarts(@RequestHeader("LoggedInUser") String userName) throws CartDetailsNotFoundException {

        List<ResponseCartDto> productsFromCart = customerService.getAllProductsFromCart(userName);

        return new ResponseEntity<>(productsFromCart, HttpStatus.OK);
    }

}
