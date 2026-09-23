package com.example.FreshFood.mapper;

import com.example.FreshFood.dto.response.OrderItemResponse;
import com.example.FreshFood.dto.response.OrderResponse;
import com.example.FreshFood.dto.response.SubOrderItemResponse;
import com.example.FreshFood.dto.response.SubOrderResponse;
import com.example.FreshFood.entity.Order;
import com.example.FreshFood.entity.SubOrder;
import com.example.FreshFood.entity.SubOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "subOrders", target = "subOrders")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "farmer.username", target = "farmerUsername")
    @Mapping(source = "subOrderItems", target = "items")
    SubOrderResponse toSubOrderResponse(SubOrder subOrder);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    SubOrderItemResponse toSubOrderItemResponse(SubOrderItem subOrderItem);
}
