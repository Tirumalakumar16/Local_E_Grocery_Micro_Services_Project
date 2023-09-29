package com.identityservice.service;

import com.identityservice.model.enums.Constants;
import com.identityservice.dtos.IdentityResponseDto;
import com.identityservice.dtos.UserCredentialsRequest;
import com.identityservice.model.UserCredentials;
import com.identityservice.repository.UserRepository;
import com.identityservice.service.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelMapper modelMapper;




    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String saveUser(UserCredentialsRequest userCredentials) {

            UserCredentials userCredentials1 = new UserCredentials();
            userCredentials1.setUsername(userCredentials.getUsername());
            userCredentials1.setRoles(userCredentials.getRole());
           userCredentials1.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
             userCredentials1.setEmailId(userCredentials.getEmailId());
          userCredentials1.setActive(true);
          userCredentials1.setCreatedOn(new Date());
          userCredentials1.setUpdatedOn(new Date());


        userRepository.save(userCredentials1);

        return "Registered succesfully.... enjoy E-cart service ...!";
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
}
