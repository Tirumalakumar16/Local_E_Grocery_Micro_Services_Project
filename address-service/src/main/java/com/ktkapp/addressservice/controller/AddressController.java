package com.ktkapp.addressservice.controller;

import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.ktkapp.addressservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    public ResponseAddressDto saveAddress(@RequestBody RequestAddressDto requestAddressDto) {

        return addressService.saveAddress(requestAddressDto);
    }

    @GetMapping("/addresses/{email}")
    public List<ResponseAddressDto> getAddresses(@PathVariable("email")String emailId) {

        List<ResponseAddressDto> address =null;
        try {
           address = addressService.getAddress(emailId);
            return address;
        } catch (AddressNotFoundWithEmail e) {
            System.out.println(e.getMessage());
        }
        return address;
    }

    @DeleteMapping("/address")
    public String deleteAddress(@RequestBody DeleteAddressRequest deleteAddress) {

        return addressService.deleteAddress(deleteAddress);
    }

    @PutMapping("/address")
    public ResponseAddressDto updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) {

        return addressService.updateAddress(updateAddressRequest);
    }
    @GetMapping("/address/{email}/{houseNumber}/{zip}")
    public ResponseAddressDto getByZipAddress(@PathVariable("email") String email,@PathVariable("houseNumber") String houseNumber,@PathVariable("zip") String zip) {

        return addressService.getByZipAddress(email,houseNumber,zip);
    }
    @GetMapping("/address/{houseNumber}")
    public ResponseAddressDto getByHouseNumber(@PathVariable("houseNumber") String houseNumber) {

        return addressService.getByHouseNumber(houseNumber);
    }
}
