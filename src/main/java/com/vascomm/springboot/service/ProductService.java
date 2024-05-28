package com.vascomm.springboot.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 11:25 PM
@Last Modified 5/28/2024 11:25 PM
Version 1.0
*/

import com.vascomm.springboot.model.Product;
import com.vascomm.springboot.model.dto.PagedResponse;
import com.vascomm.springboot.model.dto.ProductDTO;
import com.vascomm.springboot.model.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ApiResponse<PagedResponse<Product>> getAllProducts(Pageable pageable);

    ApiResponse<Product> getProductById(Integer id);

    ApiResponse<Void> deleteProductById(Integer id);


    ApiResponse<Void> updateProductById(ProductDTO product);

    ApiResponse<Integer> saveBatch(List<ProductDTO> productDTOS);

    ApiResponse<Void> save(ProductDTO productDTO);

    ApiResponse<List<Product>> searchByName(String name);
}
