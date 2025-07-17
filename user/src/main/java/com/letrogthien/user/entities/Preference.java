package com.letrogthien.user.entities;
import com.letrogthien.user.common.Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "preferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "notification_email", nullable = false)
    private boolean notificationEmail = true;

    @Column(name = "notification_push", nullable = false)
    private boolean notificationPush = true;

    @Column(name = "preferred_currency", length = 10)
    private String preferredCurrency;

    @Column(name = "preferred_platform", length = 50)
    @Enumerated(EnumType.STRING)
    private Platform preferredPlatform;

    @Column(name = "privacy_public_profile", nullable = false)
    private boolean privacyPublicProfile = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
