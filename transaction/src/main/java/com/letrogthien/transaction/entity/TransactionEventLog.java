package com.letrogthien.transaction.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_events_log")
@Data
@Builder
public class TransactionEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;

    @Column(name = "event_data", columnDefinition = "json")
    private String eventData;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logged_at", nullable = false)
    private LocalDateTime loggedAt;

    @PrePersist
    protected void onCreate() {
        loggedAt = LocalDateTime.now();
    }

    @Column(name = "actor_id", columnDefinition = "BINARY(16)")
    private UUID actorId;
}
