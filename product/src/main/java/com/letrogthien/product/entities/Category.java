package com.letrogthien.product.entities;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Category {

    @Id
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

}
