package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Order;
import com.example.FreshFood.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerUsernameOrderByCreatedAtDesc(String username);

    List<Order> findAllByOrderByCreatedAtDesc();

    Optional<Order> findByIdAndStatus(UUID id, OrderStatus status);
}

