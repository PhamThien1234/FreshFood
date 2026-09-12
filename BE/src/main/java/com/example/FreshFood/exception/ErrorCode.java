package com.example.FreshFood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USERNAME_ALREADY_EXIST(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại"),
    USER_UN_EXISTED(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "Email đã được sử dụng"),
    USER_DISABLED(HttpStatus.FORBIDDEN, "Tài khoản đã bị vô hiệu hóa"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Sai tên đăng nhập hoặc mật khẩu"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"),
    PRODUCT_ALREADY_PROCESSED(HttpStatus.CONFLICT, "Sản phẩm đã được xử lý"),
    INVALID_FILE(HttpStatus.BAD_REQUEST, "File không hợp lệ"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Tải file lên thất bại");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
