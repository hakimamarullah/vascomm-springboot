package com.vascomm.springboot.model.enums;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 7:30 PM
@Last Modified 5/28/2024 7:30 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, USER;

    @JsonCreator
    public static Role forRole(String role) {
        for (Role x : Role.values()) {
            if (x.name().equalsIgnoreCase(role)) {
                return x;
            }
        }
        return null;
    }
}
