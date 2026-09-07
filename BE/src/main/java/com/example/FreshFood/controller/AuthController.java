package com.example.FreshFood.controller;

import com.example.FreshFood.dto.request.CustomerRegisterRequest;
import com.example.FreshFood.dto.request.FarmerRegisterRequest;
import com.example.FreshFood.dto.request.LoginRequest;
import com.example.FreshFood.dto.response.LoginResponse;
import com.example.FreshFood.dto.response.UserResponse;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.service.AuthenticationService;
import com.example.FreshFood.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    private final UserService userService;

    @PostMapping("/register/customer")
    public ResponseEntity<UserResponse> registerCustomer(@Valid @RequestBody CustomerRegisterRequest customerRegisterRequest){
        UserResponse response = userService.registerCustomer(customerRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/register/farmer")
    public ResponseEntity<UserResponse> registerFarmer(@Valid @RequestBody FarmerRegisterRequest farmerRegisterRequest){
        UserResponse response = userService.registerFarmer(farmerRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

}
