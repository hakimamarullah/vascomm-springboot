package com.vascomm.springboot.model.dto;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 11:22 PM
@Last Modified 5/28/2024 11:22 PM
Version 1.0
*/

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {


    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Min(value = 0)
    private Integer stock;

    @Min(value = 0)
    private Double price;

    @NotNull
    private Boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
