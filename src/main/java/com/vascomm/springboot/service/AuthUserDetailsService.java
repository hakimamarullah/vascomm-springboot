package com.vascomm.springboot.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 6:39 PM
@Last Modified 5/28/2024 6:39 PM
Version 1.0
*/

import com.vascomm.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exist", username)));
    }
}
