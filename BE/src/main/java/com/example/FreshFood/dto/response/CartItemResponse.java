package com.example.FreshFood.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private UUID id;

    private UUID productId;

    private String productName;

    private BigDecimal price;

    private String imageUrl;

    private String category;

    private Integer quantity;

    private BigDecimal subtotal;
}
