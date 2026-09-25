package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Order;
import com.example.FreshFood.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubOrderRepository extends JpaRepository<SubOrder, UUID> {
    List<SubOrder> findByFarmerUsernameOrderByCreatedAtDesc(String farmerUsername);

}
