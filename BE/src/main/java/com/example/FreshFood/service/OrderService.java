package com.example.FreshFood.service;

import com.example.FreshFood.dto.request.OrderItemRequest;
import com.example.FreshFood.dto.request.OrderRequest;
import com.example.FreshFood.dto.response.OrderResponse;
import com.example.FreshFood.dto.response.SubOrderResponse;
import com.example.FreshFood.entity.*;
import com.example.FreshFood.enums.OrderStatus;
import com.example.FreshFood.enums.ProductStatus;
import com.example.FreshFood.enums.SubOrderStatus;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.mapper.OrderMapper;
import com.example.FreshFood.repository.OrderRepository;
import com.example.FreshFood.repository.ProductRepository;
import com.example.FreshFood.repository.SubOrderRepository;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final SubOrderRepository subOrderRepository;

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
            .totalAmount(BigDecimal.ZERO)
            .subOrders(new ArrayList<>())
            .build();
    BigDecimal totalAmount = BigDecimal.ZERO;

    Map<UUID, SubOrder> subOrderMap = new HashMap<>();

    for(OrderItemRequest itemRequest : request.getItems()){ // itemRequest: Biến đại diện cho từng phần tử trong danh sách, tại mỗi lượt lặp
        Product product = productRepository.findByIdAndStatus(itemRequest.getProductId(), ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if(product.getQuantity() < itemRequest.getQuantity())
            throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);

        BigDecimal unitPrice = product.getPrice();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

        UUID farmerId = product.getFarmer().getId();

        SubOrder subOrder = subOrderMap.get(farmerId);

        if(subOrder == null){
            subOrder = SubOrder.builder()
                    .order(order)
                    .farmer(product.getFarmer())
                    .status(SubOrderStatus.PENDING)
                    .totalAmount(BigDecimal.ZERO)
                    .subOrderItems(new ArrayList<>())
                    .build();
        }
        subOrderMap.put(farmerId, subOrder);

        SubOrderItem subOrderItem = SubOrderItem.builder()
                .subOrder(subOrder)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();

        subOrder.getSubOrderItems().add(subOrderItem);

        subOrder.setTotalAmount(subOrder.getTotalAmount().add(subtotal));

        product.setQuantity(product.getQuantity() - itemRequest.getQuantity());

        totalAmount = totalAmount.add(subtotal);
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
    }
    @Transactional(readOnly =true)
    public List<OrderResponse> getMyOrders(String username){
        List<Order> orders = orderRepository.findByCustomerUsernameOrderByCreatedAtDesc(username);
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<SubOrderResponse> getFarmerOrders(String farmerUsername) {
                List<SubOrder> subOrders = subOrderRepository.findByFarmerUsernameOrderByCreatedAtDesc(farmerUsername);

        return subOrders.stream()
                .map(orderMapper::toSubOrderResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        List<Order> orders =
                orderRepository.findAllByOrderByCreatedAtDesc();

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

}
