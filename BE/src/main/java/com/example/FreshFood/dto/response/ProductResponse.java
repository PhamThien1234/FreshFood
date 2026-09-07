package com.example.FreshFood.dto.response;

import com.example.FreshFood.enums.ProductStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String imageUrl;

    private String category;

    private ProductStatus status;

    private UUID farmerId;

    private String farmerName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
