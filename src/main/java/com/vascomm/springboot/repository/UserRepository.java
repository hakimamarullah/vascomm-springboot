package com.vascomm.springboot.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 7:42 PM
@Last Modified 5/28/2024 7:42 PM
Version 1.0
*/

import com.vascomm.springboot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findFirstByEmailOrUsernameAndEnabledTrue(String email, String username);

    Page<User> findAllByEnabledTrue(Pageable pageable);

}
