package com.customer.CustomerService.controller;


import com.customer.CustomerService.service.CustomerService;
import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class CustomerAddressController {


    private CustomerService customerService;
    @Autowired
    public CustomerAddressController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("customer/address")// working
    public ResponseAddressDto saveAddresForUser(@RequestBody RequestAddressDto requestAddressDto, @RequestHeader("loggedInUser") String userName)  {

        ResponseAddressDto responseAddressDto = customerService.saveAddress(requestAddressDto,userName);
        return responseAddressDto;
    }

    @GetMapping("/customer/addresses")
    public List<ResponseAddressDto> getAllAddressForUser(@RequestHeader("LoggedInUser") String username) throws AddressNotFoundWithEmail {


        return customerService.getAddressesFromAddressService(username);

    }
    @DeleteMapping ("/customer/address")
    public String deleteAddressWithZip(@RequestBody DeleteAddressRequest deleteAddressRequest, @RequestHeader("LoggedInUser") String userName) {

            return customerService.deleteAddressWithZip(deleteAddressRequest,userName);
    }

    @PutMapping("/customer/address")
    public ResponseAddressDto updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest, @RequestHeader("LoggedInUser") String userName) {

        ResponseAddressDto responseAddressDto = customerService.updateAddress(updateAddressRequest,userName);

        return responseAddressDto;
    }
    @GetMapping("/customer/email_zip_houseNumber")
    public ResponseAddressDto getAddressByZipAndMail(@RequestBody RequestByZipAndEmailDto requestByZipAndEmailDto, @RequestHeader("LoggedInUser") String userName) {

        return customerService.getByZipAndEmail(requestByZipAndEmailDto,userName);
    }
}
