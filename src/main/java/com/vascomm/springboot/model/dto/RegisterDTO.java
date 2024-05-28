package com.vascomm.springboot.model.dto;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 9:43 PM
@Last Modified 5/28/2024 9:43 PM
Version 1.0
*/

import com.vascomm.springboot.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    private Integer id;

    @NotBlank
    private String username;


    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private Role role;
}
