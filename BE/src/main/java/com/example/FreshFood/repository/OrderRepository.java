package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerUsernameOrderByCreatedAtDesc(String username);

    @Query("""
        SELECT DISTINCT o
        FROM Order o
        JOIN o.orderItems oi
        JOIN oi.product p
        WHERE p.farmer.username = :farmerUsername
        ORDER BY o.createdAt DESC
    """)
    List<Order> findOrdersContainingFarmerProducts(
            @Param("farmerUsername") String farmerUsername
    );
    List<Order> findAllByOrderByCreatedAtDesc();
}

