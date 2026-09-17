package com.example.FreshFood.mapper;

import com.example.FreshFood.dto.response.OrderItemResponse;
import com.example.FreshFood.dto.response.OrderResponse;
import com.example.FreshFood.entity.Order;
import com.example.FreshFood.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "items")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
