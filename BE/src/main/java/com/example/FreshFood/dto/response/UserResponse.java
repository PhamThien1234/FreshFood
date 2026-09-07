package com.example.FreshFood.dto.response;

import com.example.FreshFood.enums.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;

    private String username;

    private String email;

    private String fullName;

    private String phone;

    private String address;

    private String avatarUrl;

    private Role role;

    private Boolean enabled;
}
