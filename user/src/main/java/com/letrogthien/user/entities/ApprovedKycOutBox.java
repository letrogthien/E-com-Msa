package com.letrogthien.user.entities;

import com.letrogthien.user.common.Status;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@Document("approved_outbox")
public class ApprovedKycOutBox {
    @Id
    private UUID id;
    private UUID userId;
    private Status status;
    private ZonedDateTime createdAt;


    @PrePersist
    private void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.status = Status.PENDING;
    }


}
