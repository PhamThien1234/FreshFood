package com.example.FreshFood.controller;

import com.example.FreshFood.dto.response.CartItemResponse;
import com.example.FreshFood.entity.CartItem;
import com.example.FreshFood.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    @PreAuthorize("hasAuthority('CART_CREATE')")
    public String addToCart(@RequestParam UUID productId, @RequestParam int quantity, Authentication authentication){
        cartService.addToCart(productId,quantity,authentication.getName());

        return "Thêm sản phẩm vào giỏ hàng thành công";
    }
    @GetMapping("/items")
    @PreAuthorize("hasAuthority('CART_VIEW')")
    public List<CartItemResponse> getMyCart(Authentication authentication){
        return cartService.getMyCart(authentication.getName());
    }
}
