package com.letrogthien.user.entities;

import com.letrogthien.user.common.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seller_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerApplication {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_seller_applications_user"))
    private User user;

    @Column(name = "application_status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status applicationStatus; // e.g., PENDING, APPROVED, REJECTED, DRAFT, SUBMITTED, NEEDS_MORE_INFO

    @Column(name = "submission_date", updatable = false)
    private LocalDateTime submissionDate;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "reviewer_id")
    private UUID reviewerId; // ID of the admin/moderator who reviewed it

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // Internal notes for admins

    @OneToOne
    @JoinColumn(name = "verification_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_seller_applications_verification"))
    private UserKyc userKyc;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (submissionDate == null) {
            submissionDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
