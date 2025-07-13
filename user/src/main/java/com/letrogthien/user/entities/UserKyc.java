package com.letrogthien.user.entities;
import com.letrogthien.user.common.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_verifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserKyc {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "verification_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status verificationStatus;

    @Column(name = "face_id_front_url", length = 255)
    private String faceIdFrontUrl;

    @Column(name = "face_id_back_url", length = 255)
    private String faceIdBackUrl;

    @Column(name = "face_id_smile_url", length = 255)
    private String faceIdSmileUrl;

    @Column(name = "document_front_url", length = 255)
    private String documentFrontUrl;

    @Column(name = "document_back_url", length = 255)
    private String documentBackUrl;

    @Column(name = "version")
    private int version;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.setVerificationStatus(Status.PENDING);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}