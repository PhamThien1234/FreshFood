package com.example.FreshFood.controller;

import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavouriteService favouriteService;

    @PostMapping
    @PreAuthorize("hasAuthority('FAVORITE_CREATE')")
    public String addFavourite(@PathVariable UUID productId, Authentication authentication){
        favouriteService.addFavourite(productId, authentication.getName());

        return ("Thêm sản phẩm vào yêu thích thành công");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('FAVORITE_VIEW')")
    public List<ProductResponse> getMyFavorite(Authentication authentication){
        return favouriteService.getMyFavorite(authentication.getName());
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('FAVORITE_DELETE')")
    public String removeFavorite(@PathVariable UUID productId, Authentication authentication){
        favouriteService.removeFavorite(productId, authentication.getName());
        return ("Xóa yêu thích thành công");
    }
}
