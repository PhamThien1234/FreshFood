package com.example.FreshFood.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  CustomerRegisterRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 4, max = 50, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 6, max = 100, message = "PASSWORD_INVALID")
    private String password;

    @NotBlank(message = "FULL_NAME_REQUIRED")
    @Size(max = 100, message = "FULL_NAME_TOO_LONG")
    private String fullName;

    @Size(max = 20, message = "PHONE_TOO_LONG")
    private String phone;

    @Size(max = 255, message = "ADDRESS_TOO_LONG")
    private String address;
}
