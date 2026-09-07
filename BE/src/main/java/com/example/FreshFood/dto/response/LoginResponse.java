package com.example.FreshFood.dto.response;

import com.example.FreshFood.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {
    private String token;

    private UUID id;

    private String username;

    private String email;

    private String fullName;

    private Role role;
}
