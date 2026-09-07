package com.example.FreshFood.mapper;

import com.example.FreshFood.dto.response.UserResponse;
import com.example.FreshFood.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
