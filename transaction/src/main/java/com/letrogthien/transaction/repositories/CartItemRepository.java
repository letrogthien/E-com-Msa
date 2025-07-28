package com.letrogthien.transaction.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letrogthien.transaction.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    // Define methods for custom queries if needed
    
}
