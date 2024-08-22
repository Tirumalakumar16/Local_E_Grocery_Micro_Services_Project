package com.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.identityservice.config.KafkaPublisherClient;
import com.identityservice.dtos.*;
import com.identityservice.exceptions.PasswordNotMatchedException;
import com.identityservice.exceptions.UserNotFoundException;
import com.identityservice.service.security.JwtService;
import com.identityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins="http://localhost:5173/")
@RestController
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private KafkaPublisherClient kafkaPublisherClient;

    private ObjectMapper objectMapper;


    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, KafkaPublisherClient kafkaPublisherClient, ObjectMapper objectMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.kafkaPublisherClient = kafkaPublisherClient;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody UserCredentialsRequest request) {

        return userService.saveUser(request);
    }
    @PostMapping("/sign_in")
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

    @PutMapping("/resetpassword")
    public String resetPassword(@RequestBody RequestResetPasswordDto requestResetPasswordDto) throws UserNotFoundException {

         userService.resetPassword(requestResetPasswordDto);

        return "password changed successfully";
    }



    //This not passed the test
    @PutMapping("/changePassword")
    public String changePassword(@RequestBody RequestOldToNewPassword requestOldToNewPassword, @RequestHeader("LoggedInUser") String user) throws PasswordNotMatchedException {

        userService.changePassword(requestOldToNewPassword,user);

        return "password changed successfully";
    }

    // For Logout API maintain JWT Blacklist for tokens who ever hit the url for /logout
    //

    @GetMapping("/sample")
    public String getPass(@RequestHeader("LoggedinUser") String user) {

        return userService.getPass(user);
    }
}
