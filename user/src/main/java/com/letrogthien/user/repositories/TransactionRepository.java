package com.letrogthien.user.repositories;

import com.letrogthien.user.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    // Custom query methods can be defined here if needed
}
