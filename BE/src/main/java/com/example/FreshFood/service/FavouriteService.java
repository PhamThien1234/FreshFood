package com.example.FreshFood.service;

import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.entity.Favorite;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.ProductStatus;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.mapper.ProductMapper;
import com.example.FreshFood.repository.FavoriteRepository;
import com.example.FreshFood.repository.ProductRepository;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final FavoriteRepository favouriteRepository;

    private final ProductMapper productMapper;

    public void addFavourite(UUID productId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        Product product = productRepository.findByIdAndStatus(productId, ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        boolean exists = favouriteRepository.existsByUserIdAndProductId(user.getId(), productId);

        if(exists){
            return;
        }
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        favouriteRepository.save(favorite);
    }
    public List<ProductResponse> getMyFavorite(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        List<Favorite> favorites = favouriteRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return favorites.stream()
                .map(Favorite::getProduct)
                .map(productMapper::toProductResponse)
                .toList();
    }
}
