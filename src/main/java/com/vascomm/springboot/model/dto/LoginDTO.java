package com.vascomm.springboot.model.dto;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 8:37 PM
@Last Modified 5/28/2024 8:37 PM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "password can't be blank")
    private String password;
}
