package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerUsernameOrderByCreatedAtDesc(String username);

    List<Order> findAllByOrderByCreatedAtDesc();
}

