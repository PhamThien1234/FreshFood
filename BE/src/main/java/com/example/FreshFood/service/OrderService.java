package com.example.FreshFood.service;

import com.example.FreshFood.dto.request.OrderItemRequest;
import com.example.FreshFood.dto.request.OrderRequest;
import com.example.FreshFood.dto.response.OrderResponse;
import com.example.FreshFood.entity.Order;
import com.example.FreshFood.entity.OrderItem;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.OrderStatus;
import com.example.FreshFood.enums.ProductStatus;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.mapper.OrderMapper;
import com.example.FreshFood.repository.OrderRepository;
import com.example.FreshFood.repository.ProductRepository;
import com.example.FreshFood.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request, String Username){
        if (request.getItems() == null || request.getItems().isEmpty())
            throw new AppException(ErrorCode.ORDER_EMPTY);

    User customer = userRepository.findByUsername(Username)
            .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

    Order order = Order.builder()
            .customer(customer)
            .shippingAddress(request.getShippingAddress())
            .status(OrderStatus.PENDING)
            .totalAmount(BigDecimal.ZERO)
            .orderItems(new ArrayList<>())
            .build();
    BigDecimal totalAmount = BigDecimal.ZERO;

    for(OrderItemRequest itemRequest : request.getItems()){ // itemRequest: Biến đại diện cho từng phần tử trong danh sách, tại mỗi lượt lặp
        Product product = productRepository.findByIdAndStatus(itemRequest.getProductId(), ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if(product.getQuantity() < itemRequest.getQuantity())
            throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
        BigDecimal unitPrice = product.getPrice();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
        order.getOrderItems().add(orderItem);

        product.setQuantity(product.getQuantity() - itemRequest.getQuantity());

        totalAmount = totalAmount.add(subtotal);
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
    }


}
