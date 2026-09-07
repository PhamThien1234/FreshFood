package com.example.FreshFood.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final String code;

    public AppException(String code){
        super(code);
        this.code = code;
    }
}
