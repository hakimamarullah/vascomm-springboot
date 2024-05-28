package com.vascomm.springboot.model.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/28/2024 8:33 PM
@Last Modified 5/28/2024 8:33 PM
Version 1.0
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Integer code;
    private String message;
    private T data;

    public static ApiResponse<Void> setSuccess() {
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Success")
                .build();
    }

    public  static ApiResponse<Void> setSuccess(String message) {
        return ApiResponse.<Void>builder()
                .code(200)
                .message(message)
                .build();
    }
}
