package com.example.FreshFood.service;

import com.example.FreshFood.dto.request.CustomerRegisterRequest;
import com.example.FreshFood.dto.request.FarmerRegisterRequest;
import com.example.FreshFood.dto.response.UserResponse;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.Role;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.mapper.UserMapper;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public UserResponse registerCustomer(CustomerRegisterRequest customerRegisterRequest){
        if(userRepository.existsByUsername(customerRegisterRequest.getUsername()))
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXIST);

        if(userRepository.existsByEmail(customerRegisterRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXIST);

        User user = User.builder()
                .username(customerRegisterRequest.getUsername())
                .email(customerRegisterRequest.getEmail())
                .password(passwordEncoder.encode(customerRegisterRequest.getPassword()))
                .phone(customerRegisterRequest.getPhone())
                .address(customerRegisterRequest.getAddress())
                .fullName(customerRegisterRequest.getFullName())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
    public UserResponse registerFarmer(FarmerRegisterRequest farmerRegisterRequest){
        if(userRepository.existsByUsername(farmerRegisterRequest.getUsername()))
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXIST);

        if(userRepository.existsByEmail(farmerRegisterRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXIST);

        User user = User.builder()
                .username(farmerRegisterRequest.getUsername())
                .email(farmerRegisterRequest.getEmail())
                .password(passwordEncoder.encode(farmerRegisterRequest.getPassword()))
                .phone(farmerRegisterRequest.getPhone())
                .address(farmerRegisterRequest.getAddress())
                .fullName(farmerRegisterRequest.getFullName())
                .role(Role.FARMER)
                .enabled(true)
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

}
