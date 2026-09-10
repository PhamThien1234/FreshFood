package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Product;
import com.example.FreshFood.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStatus(ProductStatus status);

    List<Product> findByFarmerId(UUID farmerId);

    Optional<Product> findByIdAndStatus(UUID Id, ProductStatus status);

    List<Product> findByStatusAndNameContainingIgnoreCase(ProductStatus status, String name);

    List<Product> findByStatusAndCategoryIgnoreCase(ProductStatus status, String category);

    Optional<Product> findByIdAndFarmerUsername(UUID id, String username);
}
