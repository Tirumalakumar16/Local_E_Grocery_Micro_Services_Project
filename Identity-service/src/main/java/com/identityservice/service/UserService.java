package com.identityservice.service;

import com.identityservice.dtos.IdentityResponseDto;
import com.identityservice.dtos.RequestResetPasswordDto;
import com.identityservice.dtos.UserCredentialsRequest;
import com.identityservice.exceptions.UserNotFoundException;

public interface UserService {
    String saveUser(UserCredentialsRequest userCredentials);

    String getToken(String userName);

    void validate(String token);


    IdentityResponseDto getCredentials(String userName);

    IdentityResponseDto getByEmail(String email);

    void resetPassword(RequestResetPasswordDto requestResetPasswordDto) throws UserNotFoundException;
}
