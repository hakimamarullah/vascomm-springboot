package com.vascomm.springboot.model.dto;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 10:54 PM
@Last Modified 5/28/2024 10:54 PM
Version 1.0
*/

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse<T> {

    private Integer totalElements;
    private Integer totalPages;
    private Integer size;
    private List<T> content;
}
