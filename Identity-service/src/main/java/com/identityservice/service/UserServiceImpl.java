package com.identityservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.identityservice.config.KafkaPublisherClient;
import com.identityservice.dtos.*;
import com.identityservice.exceptions.PasswordNotMatchedException;
import com.identityservice.exceptions.UserNotFoundException;
import com.identityservice.model.UserCredentials;
import com.identityservice.repository.UserRepository;
import com.identityservice.service.security.JwtService;
import org.aspectj.bridge.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;
    @Autowired
    private ObjectMapper modelMapper;

    private KafkaPublisherClient kafkaPublisherClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, KafkaPublisherClient kafkaPublisherClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

        this.kafkaPublisherClient = kafkaPublisherClient;
    }

    @Override
    public String saveUser(UserCredentialsRequest userCredentials) {

            UserCredentials userCredentials1 = new UserCredentials();
            if(userCredentials.getUsername().length()<3) {
                throw new IllegalArgumentException("please enter valid UserName.");
            }
            userCredentials1.setUsername(userCredentials.getUsername());
            userCredentials1.setRoles(userCredentials.getRole().toUpperCase());
            userCredentials1.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
            if(userCredentials.getEmailId().length()<3 || !(userCredentials.getEmailId().contains("@"))) {
                throw new IllegalArgumentException("please enter valid email ID.");
            }
            userCredentials1.setEmailId(userCredentials.getEmailId());
            userCredentials1.setActive(true);
            userCredentials1.setCreatedOn(new Date());
            userCredentials1.setUpdatedOn(new Date());


        userRepository.save(userCredentials1);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setTo(userCredentials.getEmailId());
        kafkaMessage.setFrom("grocerystore4169@gmail.com");
        kafkaMessage.setSubject("Welcome to Local Grocery Store");
        kafkaMessage.setBody("Thanks for registering with Grocery Store your userId is "+userCredentials.getUsername()+" enjoy the Shopping ......."+'\n'+"Team Grocery Store!!!." );
        try {
            kafkaPublisherClient.sendMessage("userSignUp", modelMapper.writeValueAsString(kafkaMessage));
        } catch (Exception e) {
            System.out.println("Something wron in users service...");
        }
        return "Registered successfully.... enjoy E-cart service ...!";
    }



    @Override
    public String getToken(String userName) {
        return jwtService.generateToken(userName);
    }

    @Override
    public void validate(String token) {
        jwtService.validateToken(token);
    }

    @Override
    public IdentityResponseDto getCredentials(String userName) {

        Optional<UserCredentials> userCredentials = userRepository.findByUsername(userName);

        if(userCredentials.isEmpty()) {
            throw new UsernameNotFoundException("Provided user name is not exit please register...!");
        }

        IdentityResponseDto identityResponseDto= new IdentityResponseDto();

        identityResponseDto.setEmailId(userCredentials.get().getEmailId());
        identityResponseDto.setUsername(userCredentials.get().getUsername());
        identityResponseDto.setRoles(userCredentials.get().getRoles());
        identityResponseDto.setCreatedOn(userCredentials.get().getCreatedOn());

        return identityResponseDto;
    }

    @Override
    public IdentityResponseDto getByEmail(String email) {

        Optional<UserCredentials> userCredentials = userRepository.findByEmailId(email);

        if(userCredentials.isEmpty()) {
            throw new UsernameNotFoundException("Provided user name is not exit please register...!");
        }

        IdentityResponseDto identityResponseDto= new IdentityResponseDto();

        identityResponseDto.setEmailId(userCredentials.get().getEmailId());
        identityResponseDto.setUsername(userCredentials.get().getUsername());
        identityResponseDto.setRoles(userCredentials.get().getRoles());
        identityResponseDto.setCreatedOn(userCredentials.get().getCreatedOn());
        return identityResponseDto;

    }


    @Override
    public void resetPassword(RequestResetPasswordDto requestResetPasswordDto) throws UserNotFoundException {

        Optional<UserCredentials> userCredentials = userRepository.findByEmailId(requestResetPasswordDto.getEmailId());

        if(userCredentials.isEmpty()) {
            throw new UserNotFoundException("please check the email , provided email not registered...!");
        }
        UserCredentials userCredentials2 = userCredentials.get();

        if(requestResetPasswordDto.getNewPassword() != null) {

            userCredentials2.setPassword(passwordEncoder.encode(requestResetPasswordDto.getNewPassword()));
        }

        userCredentials2.setUpdatedOn(new Date());

        UserCredentials userCredentials1 = userRepository.save(userCredentials2);

    }


    @Override
    public void changePassword(RequestOldToNewPassword requestOldToNewPassword, String user) throws PasswordNotMatchedException {

        Optional<UserCredentials> userCredentials = userRepository.findByUsername(user);

        if(userCredentials.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserCredentials userCredentials1 = userCredentials.get();
//        String oldPassword = passwordEncoder.encode(requestOldToNewPassword.getOldPassword());

        if(!passwordEncoder.matches(requestOldToNewPassword.getOldPassword(),userCredentials1.getPassword()) ) {
            throw new PasswordNotMatchedException("Please check the password ,password is not same");
        }
        userCredentials1.setPassword(passwordEncoder.encode(requestOldToNewPassword.getNewPassword()));


        userRepository.save(userCredentials1);

    }


    @Override
    public String getPass(String user) {
        Optional<UserCredentials> userCredentials = userRepository.findByUsername(user);

        if(userCredentials.isEmpty()) {
            throw new UsernameNotFoundException("User Not found");
        }
        UserCredentials userCredentials1 = userCredentials.get();

        return userCredentials1.getPassword();
    }
}
