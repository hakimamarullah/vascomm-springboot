package com.vascomm.springboot.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 11:29 PM
@Last Modified 5/28/2024 11:29 PM
Version 1.0
*/

import com.vascomm.springboot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {



    Page<Product> findByIsDeletedFalse(Pageable pageable);



    List<Product> findByNameContainingIgnoreCase(String name);
}
