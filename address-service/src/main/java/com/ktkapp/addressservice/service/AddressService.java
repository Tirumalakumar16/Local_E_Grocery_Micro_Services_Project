package com.ktkapp.addressservice.service;

import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;

import java.util.List;

public interface AddressService {
    ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto);

    List<ResponseAddressDto> getAddress(String email) throws AddressNotFoundWithEmail;

    String deleteAddress(DeleteAddressRequest deleteAddress);

    ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest);

    ResponseAddressDto getByZipAddress(String email, String houseNumber,String zip);

    ResponseAddressDto getByHouseNumber(String houseNumber);
}
