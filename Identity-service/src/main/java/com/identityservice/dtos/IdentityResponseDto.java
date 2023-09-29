package com.identityservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdentityResponseDto {


    private String username;
    private String password;
    private String emailId;
    private Date createdOn;
    //for seller to add inventory only
    private String roles;




}
