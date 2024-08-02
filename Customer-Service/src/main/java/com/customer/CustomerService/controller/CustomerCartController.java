package com.customer.CustomerService.controller;

import com.cartservice.CartService.dtos.ResponseCartDto;
import com.cartservice.CartService.dtos.UpdateCartDto;
import com.cartservice.CartService.exceptions.CartDetailsNotFound;
import com.customer.CustomerService.dtos.RequestCustomerDto;
import com.customer.CustomerService.dtos.cart.RequestCustCartDto;
import com.customer.CustomerService.exceptions.CartDetailsNotFoundException;
import com.customer.CustomerService.exceptions.CartServiceUpdationException;
import com.customer.CustomerService.service.CustomerService;
import com.netflix.discovery.converters.Auto;
import com.products.ProductService.exceptions.ProductsNotAvailableWithProductName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173/")
public class CustomerCartController {

    private CustomerService customerService;
    @Autowired
    public CustomerCartController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/customer/cart")
    public ResponseEntity<ResponseCartDto> saveCart(@RequestBody RequestCustCartDto requestCustCartDto, @RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException, ProductsNotAvailableWithProductName {

        return customerService.saveCart(requestCustCartDto,userName);
    }
    @PutMapping("/customer/cart")
    public ResponseEntity<ResponseCartDto> updateCart(@RequestBody UpdateCartDto updateCartDto,@RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException, CartDetailsNotFound {
        return customerService.updatecart(updateCartDto,userName);
    }
    @GetMapping("/customer/carts")
    public ResponseEntity<List<ResponseCartDto>> getAllCarts(@RequestHeader("LoggedInUser") String userName) throws CartDetailsNotFoundException, CartDetailsNotFound, CartServiceUpdationException {

        List<ResponseCartDto> productsFromCart = customerService.getAllProductsFromCart(userName);

        return new ResponseEntity<>(productsFromCart, HttpStatus.OK);
    }

    @DeleteMapping("/customer/cart/{productName}")
    public String deleteCart(@PathVariable("productName") String productName ,@RequestHeader("LoggedInUser") String userName) throws CartServiceUpdationException, CartDetailsNotFound {
        customerService.deleteCartProduct(productName,userName);
        return "Successfully deleted from Cart...!";
    }

}
