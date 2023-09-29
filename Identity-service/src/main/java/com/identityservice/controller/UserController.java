package com.identityservice.controller;

import com.identityservice.dtos.AuthRequest;
import com.identityservice.dtos.IdentityResponseDto;
import com.identityservice.dtos.UserCredentialsRequest;
import com.identityservice.service.security.JwtService;
import com.identityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    private UserService userService;
    private JwtService jwtService;

    private AuthenticationManager authenticationManager;
    @Autowired
    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserCredentialsRequest request) {

        return userService.saveUser(request);
    }
    @PostMapping("/token")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword()));

        if(authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());

        } else  {
            throw  new UsernameNotFoundException("user is not found in data base");
        }
    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validate(token);

        return "Token is valid";
    }

    @GetMapping("/identity")
    public IdentityResponseDto getUserCredentials(@RequestHeader("loggedInUser") String userName) {

        return userService.getCredentials(userName);
    }

    @GetMapping("/identity{email}")
    public IdentityResponseDto getUserEmail(@PathVariable("email") String email) {

        return userService.getByEmail(email);
    }

}
