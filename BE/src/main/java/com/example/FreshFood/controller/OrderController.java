package com.example.FreshFood.controller;

import com.example.FreshFood.dto.request.OrderRequest;
import com.example.FreshFood.dto.response.OrderResponse;
import com.example.FreshFood.dto.response.SubOrderResponse;
import com.example.FreshFood.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request, Authentication authentication) {
        return orderService.createOrder(request, authentication.getName());
    }
    @GetMapping("/my-orders")
    @PreAuthorize("hasAuthority('ORDER_VIEW_OWN')")
    public List<OrderResponse> get(Authentication authentication) {
        return orderService.getMyOrders(authentication.getName());
    }
    @GetMapping("/farmer-orders")
    @PreAuthorize("hasAuthority('ORDER_VIEW_FARMER')")
    public List<SubOrderResponse> getFarmerOrders(Authentication authentication) {
        return orderService.getFarmerOrders(authentication.getName());
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ORDER_VIEW_ALL')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
}