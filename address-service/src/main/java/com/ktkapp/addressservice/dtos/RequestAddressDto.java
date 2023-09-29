package com.ktkapp.addressservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestAddressDto {

   private String houseNumber;
   private String landMark;
   private String city;

   private String district;
   private String zip;
   private String state;
   private String streetName;

   private EmailAddressRequest emailRequest;
}
