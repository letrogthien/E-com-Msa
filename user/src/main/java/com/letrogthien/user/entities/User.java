package com.letrogthien.user.entities;

import com.letrogthien.user.common.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(name = "is_seller", nullable = false)
    private boolean isSeller;

    @Column(name = "seller_bio", columnDefinition = "TEXT")
    private String sellerBio;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Preference preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SellerRating> sellerRatingsAsSeller;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SellerRating> sellerRatingsAsBuyer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserKyc userVerification;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();               
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.isSeller = false;
        this.sellerRatingsAsSeller = new HashSet<>();
        this.sellerRatingsAsBuyer = new HashSet<>();
        this.transactions = new HashSet<>();
        this.deletedAt = null;
        this.avatarUrl = "https://example.com/default-avatar.png"; // Default avatar URL
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
