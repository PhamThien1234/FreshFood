package com.example.FreshFood.exception;

public final class ErrorCode {
    private ErrorCode(){

    }

    public static final String USERNAME_ALREADY_EXIST = "USER_ALREADY_EXIST";
    public static final String USER_UN_EXISTED = "USER_UN_EXIST";
    public static final String EMAIL_ALREADY_EXIST = "EMAIL_ALREADY_EXIST";
    public static final String USER_DISABLED = "USER_DISABLED";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String PRODUCT_ALREADY_PROCESSED = "PRODUCT_ALREADY_PROCESSED";
}
