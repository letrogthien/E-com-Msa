package com.letrogthien.user.repositories;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.entities.ApprovedKycOutBox;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ApprovedKycOutBoxRepository extends MongoRepository<ApprovedKycOutBox, UUID> {
    List<ApprovedKycOutBox> findAllByStatusOrderByCreatedAtAsc(Status status);
}
