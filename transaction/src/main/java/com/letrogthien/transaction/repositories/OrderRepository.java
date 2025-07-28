package com.letrogthien.transaction.repositories;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByBuyerId(UUID buyerId);
    List<Order> findBySellerId(UUID sellerId);
    List<Order> findByStatus(Status status);
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT o.status as status, COUNT(o) as count FROM Order o GROUP BY o.status")
    Map<Status, Long> countByStatus();
    
    @Query("SELECT o.status as status, COUNT(o) as count FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate GROUP BY o.status")
    Map<Status, Long> countByStatusAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT DATE(o.createdAt) as date, COUNT(o) as count FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(o.createdAt)")
    Map<LocalDateTime, Long> getDailyOrderCounts(LocalDateTime startDate, LocalDateTime endDate);
}
