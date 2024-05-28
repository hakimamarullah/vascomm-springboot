package com.vascomm.springboot.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 8:39 PM
@Last Modified 5/28/2024 8:39 PM
Version 1.0
*/

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vascomm.springboot.model.User;
import com.vascomm.springboot.model.dto.LoginDTO;
import com.vascomm.springboot.model.dto.PagedResponse;
import com.vascomm.springboot.model.response.ApiResponse;
import com.vascomm.springboot.repository.UserRepository;
import com.vascomm.springboot.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthenticationProvider authenticationProvider;

    private final JwtEncoder jwtEncoder;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @Value("${jwt.token.age:3600}")
    private Long jwtExpiry;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationProvider authenticationProvider, JwtEncoder jwtEncoder, BCryptPasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.jwtEncoder = jwtEncoder;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }


    @Override
    public ApiResponse<User> registerUser(User user) {
        Optional<User> userDb = userRepository.findFirstByEmailOrUsernameAndEnabledTrue(user.getEmail(), user.getUsername());

        if (userDb.isPresent()) {
            return ApiResponse.<User>builder()
                    .message("username or email already registered")
                    .code(409)
                    .data(null)
                    .build();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ApiResponse.<User>builder()
                .message("user registration is successful")
                .code(201)
                .data(userRepository.save(user))
                .build();
    }

    @Override
    public ApiResponse<String> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        User user = (User) authentication.getPrincipal();

        var now = Instant.now();
        var scope = authentication.getAuthorities().parallelStream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claim = JwtClaimsSet.builder()
                .issuer("com.vascomm")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtExpiry))
                .subject(String.format("%s,%s", user.getId(), user.getUsername()))
                .claim("roles", scope)
                .build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(claim)).getTokenValue();
        return ApiResponse.<String>builder()
                .code(200)
                .data(token)
                .message("login successful")
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        user.ifPresent(value -> value.setEnabled(false));
        String message = user.isPresent() ? "User has been deleted" : "User does not exist";
        return ApiResponse.<Void>builder()
                .code(200)
                .message(message)
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<Void> updateUser(User user) {
        Optional<User> userDb = userRepository.findById(user.getId());
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(200);

        if (userDb.isEmpty()) {
            response.setMessage("User does not exist");
            return response;
        }
        user.setId(userDb.get().getId());
        userRepository.save(user);
        response.setMessage("user's details has been updated");
        return response;
    }

    @Override
    public ApiResponse<PagedResponse<User>> getAllActiveUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllByEnabledTrue(pageable);
        PagedResponse<User> pagedResponse = objectMapper.convertValue(users, new TypeReference<>() {
        });
        return ApiResponse.<PagedResponse<User>>builder()
                .code(200)
                .message("Success Get Users")
                .data(pagedResponse)
                .build();
    }
}
