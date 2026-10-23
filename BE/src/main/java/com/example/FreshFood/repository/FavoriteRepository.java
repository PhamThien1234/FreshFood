package com.example.FreshFood.repository;

import com.example.FreshFood.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    List<Favorite> findByUserIdOrderByCreatedAtDesc(UUID userId);

    void deleteByUserIdAndProductId(UUID userId, UUID productId);
}
