package com.ktkapp.addressservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestByZipAndEmailDto {

    private String emailId;
    private String zip;
    private String houseNumber;
}
