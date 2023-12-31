package com.customer.CustomerService.feignclients;

import com.ktkapp.addressservice.dtos.DeleteAddressRequest;
import com.ktkapp.addressservice.dtos.RequestAddressDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.ktkapp.addressservice.dtos.UpdateAddressRequest;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Address-service",path = "/www.localGrocery.com/address/api")
public interface AddressFeignClient {

    @PostMapping("/address")
    public ResponseAddressDto saveAddress(@RequestBody RequestAddressDto requestAddressDto);

    @GetMapping("/addresses/{email}")
    public List<ResponseAddressDto> getAddresses(@PathVariable("email") String emailId) throws AddressNotFoundWithEmail;

    @DeleteMapping("/address")
    public String deleteAddress(@RequestBody DeleteAddressRequest deleteAddress) throws AddressNotFoundWithEmail;

    @PutMapping("/address")
    public ResponseAddressDto updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) throws AddressNotFoundWithEmail;


    @GetMapping("/address/{houseNumber}")
    public ResponseAddressDto getByHouseNumber(@PathVariable("houseNumber") String houseNumber) throws AddressNotFoundWithEmail;


    @GetMapping("/address/{email}/{houseNumber}/{zip}")
    public ResponseAddressDto getByZipAddress(@PathVariable("email") String email,@PathVariable("houseNumber") String houseNumber,@PathVariable("zip") String zip) throws AddressNotFoundWithEmail;
}
