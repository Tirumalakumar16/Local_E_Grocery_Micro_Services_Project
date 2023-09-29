package com.identityservice.service.security;

import com.identityservice.config.MyUsers;
import com.identityservice.model.UserCredentials;
import com.identityservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class UserInfoDetails implements UserDetailsService {
        @Autowired
        private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> user =userRepository.findByUsername(username);
        user.orElseThrow(() ->new UsernameNotFoundException(String.format("%s not exist",username)));
        return user.map(MyUsers::new).get();
    }
}
