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
    public List<ResponseAddressDto> getAddresses(@PathVariable("email")String emailId) throws AddressNotFoundWithEmail {


           return addressService.getAddress(emailId);

    }

    @DeleteMapping("/address")
    public String deleteAddress(@RequestBody DeleteAddressRequest deleteAddress) throws AddressNotFoundWithEmail {

        return addressService.deleteAddress(deleteAddress);
    }

    @PutMapping("/address")
    public ResponseAddressDto updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) throws AddressNotFoundWithEmail {

        return addressService.updateAddress(updateAddressRequest);
    }
    @GetMapping("/address/{email}/{houseNumber}/{zip}")
    public ResponseAddressDto getByZipAddress(@PathVariable("email") String email,@PathVariable("houseNumber") String houseNumber,@PathVariable("zip") String zip) throws AddressNotFoundWithEmail {

        return addressService.getByZipAddress(email,houseNumber,zip);
    }
    @GetMapping("/address/{houseNumber}")
    public ResponseAddressDto getByHouseNumber(@PathVariable("houseNumber") String houseNumber) throws AddressNotFoundWithEmail {

        return addressService.getByHouseNumber(houseNumber);
    }
}
