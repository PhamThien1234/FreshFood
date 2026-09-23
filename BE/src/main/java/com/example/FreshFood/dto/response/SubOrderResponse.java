package com.example.FreshFood.dto.response;

import com.example.FreshFood.enums.SubOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class SubOrderResponse {
    private UUID id;

    private String farmerUsername;

    private BigDecimal totalAmount;

    private SubOrderStatus status;

    private LocalDateTime createdAt;

    private List<SubOrderItemResponse> items;
}
