package com.identityservice.dtos;

import com.identityservice.model.UserCredentials;
import com.identityservice.model.enums.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCredentialsRequest {

    private String username;
    private String password;
    private String emailId;
    private String role;

}
