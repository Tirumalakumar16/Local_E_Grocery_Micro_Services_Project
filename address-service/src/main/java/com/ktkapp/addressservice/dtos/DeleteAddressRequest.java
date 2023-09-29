package com.ktkapp.addressservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAddressRequest {

    private String zip;
    private String houseNumber;
    private EmailAddressRequest emailAddressRequest;
}
