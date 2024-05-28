package com.vascomm.springboot.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 7:27 PM
@Last Modified 5/28/2024 7:27 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vascomm.springboot.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;


    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private Boolean enabled = true;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
