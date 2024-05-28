package com.vascomm.springboot.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 11:41 PM
@Last Modified 5/28/2024 11:41 PM
Version 1.0
*/

import com.vascomm.springboot.constants.RoleType;
import com.vascomm.springboot.model.Product;
import com.vascomm.springboot.model.dto.PagedResponse;
import com.vascomm.springboot.model.dto.ProductDTO;
import com.vascomm.springboot.model.response.ApiResponse;
import com.vascomm.springboot.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<Product>>> getAllProducts(@ParameterObject Pageable pageable) {
        var response = productService.getAllProducts(pageable);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Integer id) {
        var response = productService.getProductById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> getProductById(@RequestParam String name) {
        var response = productService.searchByName(name);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.ADMIN})
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable Integer id) {
        var response = productService.deleteProductById(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @PutMapping("")
    @RolesAllowed({RoleType.ADMIN})
    public ResponseEntity<ApiResponse<Void>> updateProductById(@RequestBody @Valid ProductDTO productDTO) {
        var response = productService.updateProductById(productDTO);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @PostMapping("/batch-insert")
    @RolesAllowed({RoleType.ADMIN})
    public ResponseEntity<ApiResponse<Integer>> saveBatch(@RequestBody @Valid List<ProductDTO> productDTOs) {
        var response = productService.saveBatch(productDTOs);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @PostMapping("/single-insert")
    @RolesAllowed({RoleType.ADMIN})
    public ResponseEntity<ApiResponse<Void>> save(@RequestBody @Valid ProductDTO productDTO) {
        var response = productService.save(productDTO);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }


}
