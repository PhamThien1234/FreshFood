package com.example.FreshFood.controller;

import com.example.FreshFood.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/item")
    @PreAuthorize("hasAuthority('CART_CREATE')")
    public String addToCart(@RequestParam UUID productId, @RequestParam int quantity, Authentication authentication){
        cartService.addToCart(productId,quantity,authentication.getName());

        return "Thêm sản phẩm vào giỏ hàng thành công";
    }
}
