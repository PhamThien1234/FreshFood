package com.example.FreshFood.service;

import com.example.FreshFood.dto.response.CartItemResponse;
import com.example.FreshFood.entity.CartItem;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.ProductStatus;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.repository.CartItemRepository;
import com.example.FreshFood.repository.ProductRepository;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public void addToCart(UUID productId, Integer quantity, String username) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("So luong phai lon hon 0!");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));
        Product product = productRepository.findByIdAndStatus(productId, ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getQuantity() < quantity) {
            throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
        }
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(user.getId(), productId).orElse(null);

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .product(product)
                    .user(user)
                    .quantity(quantity)
                    .build();
        } else {
            int newQuantity = cartItem.getQuantity() + quantity;

            if (product.getQuantity() < newQuantity){
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }
            cartItem.setQuantity(newQuantity);
        }
        cartItemRepository.save(cartItem);
    }
    @Transactional(readOnly = true)
    public List<CartItemResponse> getMyCart(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));
        return cartItemRepository.findByUserId(user.getId())
                .stream() // Chuyển mỗi CartItem thành CartItemResponse
                .map(
                        cartItem -> {
                            Product product = cartItem.getProduct();

                            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

                        return CartItemResponse.builder()
                                .id(cartItem.getId())
                                .productId(product.getId())
                                .productName(product.getName())
                                .price(product.getPrice())
                                .imageUrl(product.getImageUrl())
                                .category(product.getCategory())
                                .quantity(product.getQuantity())
                                .subtotal(subtotal)
                                .build();

                        })
                .toList()
                ;
    }

}
