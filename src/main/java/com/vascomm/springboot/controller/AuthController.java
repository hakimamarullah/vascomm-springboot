package com.vascomm.springboot.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 8:13 PM
@Last Modified 5/28/2024 8:13 PM
Version 1.0
*/

import com.vascomm.springboot.model.dto.LoginDTO;
import com.vascomm.springboot.model.response.ApiResponse;
import com.vascomm.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginDTO loginDTO) {
        var response = userService.login(loginDTO);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }


}
