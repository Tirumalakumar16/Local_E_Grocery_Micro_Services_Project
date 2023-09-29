package com.identityservice.service;

import com.identityservice.dtos.IdentityResponseDto;
import com.identityservice.dtos.UserCredentialsRequest;
import com.identityservice.model.UserCredentials;

public interface UserService {
    String saveUser(UserCredentialsRequest userCredentials);

    String getToken(String userName);

    void validate(String token);


    IdentityResponseDto getCredentials(String userName);

    IdentityResponseDto getByEmail(String email);
}
