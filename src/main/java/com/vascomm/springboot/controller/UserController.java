package com.vascomm.springboot.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 10:31 PM
@Last Modified 5/28/2024 10:31 PM
Version 1.0
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vascomm.springboot.constants.RoleType;
import com.vascomm.springboot.model.User;
import com.vascomm.springboot.model.dto.PagedResponse;
import com.vascomm.springboot.model.dto.RegisterDTO;
import com.vascomm.springboot.model.response.ApiResponse;
import com.vascomm.springboot.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RolesAllowed({RoleType.ADMIN})
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid RegisterDTO registerDTO) {
        User user = new ObjectMapper().convertValue(registerDTO, User.class);
        var response = userService.registerUser(user);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String username) {
        var response = userService.deleteUserByUsername(username);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Void>> updateUser(@RequestBody @Valid RegisterDTO registerDTO) {
        User user = new ObjectMapper().convertValue(registerDTO, User.class);
        var response = userService.updateUser(user);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("/view-all")
    public ResponseEntity<ApiResponse<PagedResponse<User>>> viewAllUsers(@ParameterObject Pageable pageable) {
        var response = userService.getAllActiveUsers(pageable);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}
