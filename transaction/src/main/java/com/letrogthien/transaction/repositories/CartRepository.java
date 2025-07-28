package com.letrogthien.transaction.repositories;

import com.letrogthien.transaction.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);
    
    @Query("SELECT c FROM Cart c JOIN c.items i WHERE i.id = :itemId")
    Optional<Cart> findByItemId(UUID itemId);
}
