package com.letrogthien.user.dtos;

import com.letrogthien.user.common.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerApplicationDto {

    private UUID id;
    private UUID userId;
    private Status applicationStatus;
    private LocalDateTime submissionDate;
    private LocalDateTime reviewDate;
    private UUID reviewerId;
    private String rejectionReason;
    private String notes;
    private UUID userVerificationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
