package com.ktkapp.addressservice.service;

import com.ktkapp.addressservice.dtos.*;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;

import java.util.List;

public interface AddressService {
    ResponseAddressDto saveAddress(RequestAddressDto requestAddressDto);

    List<ResponseAddressDto> getAddress(String email) throws AddressNotFoundWithEmail;

    String deleteAddress(DeleteAddressRequest deleteAddress) throws AddressNotFoundWithEmail;

    ResponseAddressDto updateAddress(UpdateAddressRequest updateAddressRequest) throws AddressNotFoundWithEmail;

    ResponseAddressDto getByZipAddress(String email, String houseNumber,String zip) throws AddressNotFoundWithEmail;

    ResponseAddressDto getByHouseNumber(String houseNumber) throws AddressNotFoundWithEmail;
}
