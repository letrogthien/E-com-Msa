package com.letrogthien.product.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.product.entities.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    // Additional query methods can be defined here if needed
}
