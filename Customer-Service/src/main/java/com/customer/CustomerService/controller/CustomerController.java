package com.customer.CustomerService.controller;

import com.customer.CustomerService.dtos.RequestCustomerDto;
import com.customer.CustomerService.dtos.ResponseCustomerDto;
import com.customer.CustomerService.exceptions.CustomerDetailsNotAvailable;
import com.customer.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseCustomerDto saveCustomer(@RequestBody RequestCustomerDto requestCustomerDto, @RequestHeader("LoggedInUser") String userName) {

        return customerService.saveCustomer(requestCustomerDto,userName);

    }

    @GetMapping("/customer")
    public ResponseCustomerDto getCustomer(@RequestHeader("LoggedInUser") String userName) throws CustomerDetailsNotAvailable {


            return customerService.getCustomer(userName);

    }

    @GetMapping("/customers")
    public List<ResponseCustomerDto> getAllCustomers(@RequestHeader("LoggedInUser") String userName) throws CustomerDetailsNotAvailable {



            return customerService.getAll(userName);

    }


}
