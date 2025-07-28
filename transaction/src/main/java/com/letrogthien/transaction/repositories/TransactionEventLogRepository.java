package com.letrogthien.transaction.repositories;

import com.letrogthien.transaction.entity.TransactionEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionEventLogRepository extends JpaRepository<TransactionEventLog, UUID> {
    List<TransactionEventLog> findByOrderId(UUID orderId);
    List<TransactionEventLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
