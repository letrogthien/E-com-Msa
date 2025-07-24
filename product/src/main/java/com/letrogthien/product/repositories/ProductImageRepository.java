package com.letrogthien.product.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.product.entities.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    // Additional query methods can be defined here if needed
}
