package com.vascomm.springboot.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 11:29 PM
@Last Modified 5/28/2024 11:29 PM
Version 1.0
*/

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vascomm.springboot.model.Product;
import com.vascomm.springboot.model.dto.PagedResponse;
import com.vascomm.springboot.model.dto.ProductDTO;
import com.vascomm.springboot.model.response.ApiResponse;
import com.vascomm.springboot.repository.ProductRepository;
import com.vascomm.springboot.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ApiResponse<PagedResponse<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);
        PagedResponse<Product> response = new PagedResponse<>();
        response.setContent(products.getContent());
        response.setSize(products.getSize());
        response.setTotalElements((int) products.getTotalElements());
        response.setTotalPages(products.getTotalPages());

        return ApiResponse.<PagedResponse<Product>>builder()
                .code(200)
                .message("Success Get Products")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<Product> getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        ApiResponse<Product> response = new ApiResponse<>();
        response.setCode(200);

        if (product.isEmpty()) {
            response.setMessage(String.format("Product with id: %d does not exist", id));
            return response;
        }
        response.setMessage("Success Get Product");
        response.setData(product.get());
        return response;
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(old -> old.setIsDeleted(true));
        return ApiResponse.setSuccess("success delete");
    }

    @Transactional
    @Override
    public ApiResponse<Void> updateProductById(ProductDTO product) {
        Optional<Product> productDb = productRepository.findById(Math.toIntExact(product.getId()));

        if (productDb.isEmpty()) {
            return ApiResponse.<Void>builder()
                    .code(200)
                    .message(String.format("Product with id: %d does not exist", product.getId()))
                    .build();
        }
        Product updated = objectMapper.convertValue(product, Product.class);
        updated.setId(productDb.get().getId());
        productRepository.save(updated);
        return ApiResponse.setSuccess("success update product");
    }

    @Transactional
    @Override
    public ApiResponse<Integer> saveBatch(List<ProductDTO> productDTOS) {
        List<Product> products = objectMapper.convertValue(productDTOS, new TypeReference<>() {
        });

        productRepository.saveAll(products);
        return ApiResponse.<Integer>builder()
                .code(201)
                .message("success insert batch")
                .data(productDTOS.size())
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<Void> save(ProductDTO productDTO) {
        Product product = objectMapper.convertValue(productDTO, new TypeReference<>() {
        });

        productRepository.save(product);
        return ApiResponse.<Void>builder()
                .code(201)
                .message("success insert product")
                .build();
    }

    @Override
    public ApiResponse<List<Product>> searchByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return ApiResponse.<List<Product>>builder()
                .code(200)
                .message("search product done")
                .data(products)
                .build();
    }
}
