package com.ktkapp.addressservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {

    private String houseNumber;
    private String landMark;
    private String city;
    private String district;
    private String zip;
    private String state;
    private String streetName;

    private EmailAddressRequest emailRequest;
}
